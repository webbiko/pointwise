package com.pointwise.delegate;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import com.pointwise.consumer.service.PointwiseSyncService;
import com.pointwise.entity.vo.AlarmServiceVo;
import com.pointwise.producer.alarm.UserDataAlarmManager;
import com.pointwise.producer.service.PointwiseService;
import com.pointwise.util.Constants;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * Created by wbatista on 2/15/16.
 */
public class CustomUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

    private Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler;
    private Context mContext;

    public void setDefaultUncaughtExceptionHandler(Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler, Context context) {
        this.defaultUncaughtExceptionHandler = defaultUncaughtExceptionHandler;
        this.mContext = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (this.defaultUncaughtExceptionHandler != null) {
            if (mContext != null) {
                PackageManager manager = mContext.getPackageManager();
                PackageInfo info = null;
                try {
                    info = manager.getPackageInfo(mContext.getPackageName(), 0);
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("error", e.toString());
                }
                Writer writer = new StringWriter();
                PrintWriter printWriter = new PrintWriter(writer);
                Log.e("info", printWriter.toString());

                // at this point we can log into local files and send information to crashlytics and other stuffs
                final AlarmServiceVo userDataCollectorServiceVo =
                        new AlarmServiceVo(Constants.AlarmManager.AlarmManagerRequestCode.COLLECT_USER_DATA,
                                Constants.AlarmManager.COLLECT_USER_DATA_DELAY_MILLISECONDS,
                                Constants.AlarmManager.COLLECT_USER_DATA_PERIODIC_TIME_MILLISECONDS);
                new UserDataAlarmManager(mContext, PointwiseService.class).setUpAlarmManager(userDataCollectorServiceVo);

                final AlarmServiceVo userDataPeekerServiceVo =
                        new AlarmServiceVo(Constants.AlarmManager.AlarmManagerRequestCode.PEEK_USER_DATA,
                                Constants.AlarmManager.PEEK_USER_DATA_DELAY_MILLISECONDS,
                                Constants.AlarmManager.PEEK_USER_DATA_PERIODIC_TIME_MILLISECONDS);
                new UserDataAlarmManager(mContext, PointwiseSyncService.class).setUpAlarmManager(userDataPeekerServiceVo);
            }
        }
    }
}