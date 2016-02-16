package com.pointwise.consumer.service;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;
import android.support.v4.content.LocalBroadcastManager;

import com.pointwise.entity.UserData;
import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;
import com.pointwise.producer.service.PointwiseServiceHandler;
import com.pointwise.util.Constants;

/**
 * Created by wbatista on 2/14/16.
 */
public class PointwiseSyncService extends BaseService {

    private volatile HandlerThread mHandlerThread;

    private PointwiseServiceHandler mPointwiseServiceHandler;

    private ApplicationConfiguration mApplicationConfiguration;

    private Log mLog;

    private UserData mUserData;

    // every time unreliableCountDown == 2 a runtime exception will be thrown
    // when it reaches value 0 it is restarted to 10
    private int unreliableCountDown = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        // An Android handler thread internally operates on a looper.
        this.mHandlerThread = new HandlerThread("PointwiseSyncService.HandlerThread");
        this.mHandlerThread.start();
        // An Android service handler is a handler running on a specific background thread.
        this.mPointwiseServiceHandler = new PointwiseServiceHandler(mHandlerThread.getLooper(), this);

        this.mApplicationConfiguration = new ApplicationConfiguration();
        this.mLog = new Log();
        this.mLog.setApplicationConfiguration(this.mApplicationConfiguration);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Message peekdataMessage = mPointwiseServiceHandler.obtainMessage();

        final Bundle bundle = intent.getExtras();
        peekdataMessage.arg1 = startId;
        peekdataMessage.arg2 = bundle.getInt(Constants.Service.COMMAND_CODE_KEY);
        peekdataMessage.obj = new UIResultReceiver(mPointwiseServiceHandler);
        this.mPointwiseServiceHandler.sendMessage(peekdataMessage);
        return START_REDELIVER_INTENT;
    }

    @SuppressLint("ParcelCreator")
    private class UIResultReceiver extends ResultReceiver {

        public UIResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            if (resultCode == Constants.Service.ResultReceiverCode.SUCCESS) {
                if(resultData.containsKey(Constants.BUNDLE_USER_DATA)) {
                    mUserData = resultData.getParcelable(Constants.BUNDLE_USER_DATA);
                }

                final Intent updateUIIntent;
                mLog.logDebug(Constants.LOG_TAG, "Unreliable count down = " + String.valueOf(unreliableCountDown));
                if(unreliableCountDown == 2) {
                    updateUIIntent = new Intent(Constants.BroadcastEvents.UNRELIABLE_NETWORK);
                } else{
                    updateUIIntent = new Intent(Constants.BroadcastEvents.USER_DATA_PICKED);
                }
                updateUIIntent.putExtra(Constants.BUNDLE_USER_DATA, mUserData);
                updateUIIntent.putExtra(Constants.BUNDLE_QUEUE_SIZE, resultData.getInt(Constants.BUNDLE_QUEUE_SIZE));
                updateUIIntent.putExtra(Constants.Service.RESULT_RECEIVER_KEY,
                        new ResultReceiver(new Handler(Looper.getMainLooper())) {

                            @Override
                            protected void onReceiveResult(int resultCode, Bundle resultData) {

                                if (resultCode == Constants.Service.ResultReceiverCode.SUCCESS) {
                                    // data was updated successfully
                                    mUserData = null;
                                } else if(resultCode == Constants.Service.ResultReceiverCode.FAILURE) {
                                    final Message message = mPointwiseServiceHandler.obtainMessage();
                                    message.arg2 = Constants.Service.CommandCode.COLLECT_USER_DATA;
                                    message.setData(resultData);
                                    mLog.logDebug(Constants.LOG_TAG, "Sending user data back to queue");
                                    mPointwiseServiceHandler.sendMessage(message);
                                }
                                --unreliableCountDown;
                                if(unreliableCountDown == 0) {
                                    unreliableCountDown = 10;
                                }
                            }

                        });

                LocalBroadcastManager.getInstance(PointwiseSyncService.this).sendBroadcast(updateUIIntent);
            } else {
                final Message message = mPointwiseServiceHandler.obtainMessage();
                message.arg2 = Constants.Service.CommandCode.COLLECT_USER_DATA;
                final Bundle userDataBundle = new Bundle();
                userDataBundle.putParcelable(Constants.BUNDLE_USER_DATA, mUserData);
                message.setData(userDataBundle);
                mPointwiseServiceHandler.sendMessage(message);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}