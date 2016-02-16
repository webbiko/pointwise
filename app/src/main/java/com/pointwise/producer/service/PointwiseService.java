package com.pointwise.producer.service;

import android.content.Intent;
import android.os.Bundle;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;

import com.pointwise.delegate.OperationListener;
import com.pointwise.entity.OperationError;
import com.pointwise.entity.UserData;
import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;
import com.pointwise.manager.UserDataManager;
import com.pointwise.util.Constants;

import java.util.List;

/**
 * Created by wbatista on 2/12/16.
 * This class is responsible for generating and collecting user data then put it into queue
 */
public class PointwiseService extends BaseService {

    private volatile HandlerThread mHandlerThread;

    private PointwiseServiceHandler mPointwiseServiceHandler;

    private ApplicationConfiguration mApplicationConfiguration;

    private Log mLog;

    private UserDataManager mUserDataManager;

    public static final String BUNDLE_USER_DATA = "BUNDLE_USER_DATA";

    @Override
    public void onCreate() {
        super.onCreate();
        // An Android handler thread internally operates on a looper.
        this.mHandlerThread = new HandlerThread("PointwiseService.HandlerThread");
        this.mHandlerThread.start();
        // An Android service handler is a handler running on a specific background thread.
        this.mPointwiseServiceHandler = new PointwiseServiceHandler(mHandlerThread.getLooper(), this);

        this.mApplicationConfiguration = new ApplicationConfiguration();
        this.mLog = new Log();
        this.mLog.setApplicationConfiguration(this.mApplicationConfiguration);
        this.mUserDataManager = new UserDataManager();
        this.mUserDataManager.setApplicationConfiguration(this.mApplicationConfiguration);
        this.mUserDataManager.setLog(this.mLog);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getExtras() != null) {
            final Message message = mPointwiseServiceHandler.obtainMessage();

            final Bundle bundle = intent.getExtras();

            message.arg1 = startId;
            message.arg2 = bundle.getInt(Constants.Service.COMMAND_CODE_KEY);
            if(bundle.containsKey(Constants.Service.RESULT_RECEIVER_KEY)) {
                message.obj = bundle.get(Constants.Service.RESULT_RECEIVER_KEY);
            }

            switch(message.arg2) {
                case Constants.Service.CommandCode.COLLECT_USER_DATA: {
                    this.mUserDataManager.generateUserData(new OperationListener<UserData>() {
                        @Override
                        public void onOperationSuccess(UserData userData) {
                            if(bundle.containsKey(Constants.Service.ARGUMENTS_KEY)) {
                                bundle.getBundle(Constants.Service.ARGUMENTS_KEY)
                                        .putParcelable(BUNDLE_USER_DATA, userData);
                                message.setData(bundle);
                            } else {
                                final Bundle parcelable = new Bundle();
                                parcelable.putParcelable(BUNDLE_USER_DATA, userData);
                                message.setData(parcelable);
                            }

                            mPointwiseServiceHandler.sendMessage(message);
                        }

                        @Override
                        public void onOperationError(UserData userData, List<OperationError> errors) {
                            super.onOperationError(userData, errors);
                            if (errors != null) {
                                if (errors.isEmpty()) {
                                    mLog.logError(Constants.LOG_TAG, "An error has occurred while generating user data");
                                } else {
                                    for (OperationError error : errors) {
                                        mLog.logError(Constants.LOG_TAG, String.format("[%s] - %s", error.getErrorCode(), error.getErrorMessage()));
                                    }
                                }
                            }
                        }
                    });
                } break;
                case Constants.Service.CommandCode.PEEK_USER_DATA: {
                    this.mPointwiseServiceHandler.sendMessage(message);
                } break;
            }
        }
        return START_REDELIVER_INTENT;
    }

    // Defines the shutdown sequence
    @Override
    public void onDestroy() {
        // Cleanup service before destruction
        mHandlerThread.quit();
    }
}