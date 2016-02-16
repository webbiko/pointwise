package com.pointwise.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;

import com.pointwise.delegate.CustomUncaughtExceptionHandler;

/**
 * Created by wbatista on 2/12/16.
 */
public abstract class BaseActivity extends AppCompatActivity {
    // This class should place actions that are common for all activities

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        CustomUncaughtExceptionHandler exceptionHandler = new CustomUncaughtExceptionHandler();
        exceptionHandler.setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        CustomUncaughtExceptionHandler exceptionHandler = new CustomUncaughtExceptionHandler();
        exceptionHandler.setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);
    }
}