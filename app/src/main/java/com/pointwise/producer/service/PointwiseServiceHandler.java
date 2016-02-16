package com.pointwise.producer.service;

import android.app.Service;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.ResultReceiver;

import com.pointwise.entity.UserData;
import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;
import com.pointwise.util.Constants;

import java.lang.ref.WeakReference;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Created by wbatista on 2/13/16.
 */
public class PointwiseServiceHandler extends Handler {

    private static PriorityQueue<UserData> mPriorityQueue;

    private Log mLog;

    private ApplicationConfiguration mApplicationConfiguration;

    private WeakReference<Service> mServiceReference;

    private static Object mLock = new Object();

    public PointwiseServiceHandler(final Looper looper, final Service pointwiseService) {
        super(looper);
        this.mApplicationConfiguration = new ApplicationConfiguration();
        this.mLog = new Log();
        this.mLog.setApplicationConfiguration(this.mApplicationConfiguration);
        this.mServiceReference = new WeakReference<Service>(pointwiseService);

        // dont forget to put comparator
        this.mPriorityQueue = new PriorityQueue<>(10, new Comparator<UserData>() {

            @Override
            public int compare(UserData userDataOne, UserData userDataTwo) {
                if (userDataOne.getWeight() > userDataTwo.getWeight() ||
                        userDataOne.getWeight() == userDataTwo.getWeight() && userDataOne.getDate().before(userDataTwo.getDate())) {
                    return -1;
                } else if (userDataOne.equals(userDataTwo.getWeight())) {
                    return 0;
                }
                return 1;
            }
        });
    }

    // Incoming messages are treated here
    @Override
    public void handleMessage(Message message) {
        synchronized (mLock) {
            switch (message.arg2) {
                case Constants.Service.CommandCode.COLLECT_USER_DATA: {
                    final Bundle bundle = message.getData();
                    if (bundle.containsKey(PointwiseService.BUNDLE_USER_DATA)) {
                        final UserData userData = bundle.getParcelable(PointwiseService.BUNDLE_USER_DATA);
                        mPriorityQueue.offer(userData);
                    }
                } break;
                case Constants.Service.CommandCode.PEEK_USER_DATA: {
                    if (this.mPriorityQueue != null && !this.mPriorityQueue.isEmpty()) {
                        final ResultReceiver resultReceiver = (ResultReceiver) message.obj;
                        final Bundle bundle = new Bundle();
                        bundle.putParcelable(Constants.BUNDLE_USER_DATA, this.mPriorityQueue.poll());
                        bundle.putInt(Constants.BUNDLE_QUEUE_SIZE, mPriorityQueue.size());
                        resultReceiver.send(Constants.Service.ResultReceiverCode.SUCCESS, bundle);
                    }
                } break;
            }
        }
    }
}