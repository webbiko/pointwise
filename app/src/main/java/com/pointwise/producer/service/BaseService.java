package com.pointwise.producer.service;

import android.app.Service;

import com.pointwise.delegate.CustomUncaughtExceptionHandler;

/**
 * Created by wbatista on 2/15/16.
 */
public abstract class BaseService extends Service {
    @Override
    public void onCreate() {
        super.onCreate();
        CustomUncaughtExceptionHandler exceptionHandler = new CustomUncaughtExceptionHandler();
        exceptionHandler.setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}
