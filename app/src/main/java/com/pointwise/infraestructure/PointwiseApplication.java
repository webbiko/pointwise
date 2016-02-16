package com.pointwise.infraestructure;

import android.app.Application;

import com.pointwise.consumer.service.PointwiseSyncService;
import com.pointwise.delegate.CustomUncaughtExceptionHandler;
import com.pointwise.entity.vo.AlarmServiceVo;
import com.pointwise.producer.alarm.UserDataAlarmManager;
import com.pointwise.producer.service.PointwiseService;
import com.pointwise.util.Constants;

/**
 * Created by wbatista on 2/12/16.
 */
public class PointwiseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        CustomUncaughtExceptionHandler exceptionHandler = new CustomUncaughtExceptionHandler();
        exceptionHandler.setDefaultUncaughtExceptionHandler(Thread.getDefaultUncaughtExceptionHandler(), this);
        Thread.setDefaultUncaughtExceptionHandler(exceptionHandler);

        final AlarmServiceVo userDataCollectorServiceVo =
                new AlarmServiceVo(Constants.AlarmManager.AlarmManagerRequestCode.COLLECT_USER_DATA,
                Constants.AlarmManager.COLLECT_USER_DATA_DELAY_MILLISECONDS,
                Constants.AlarmManager.COLLECT_USER_DATA_PERIODIC_TIME_MILLISECONDS);
        new UserDataAlarmManager(this, PointwiseService.class).setUpAlarmManager(userDataCollectorServiceVo);

        final AlarmServiceVo userDataPeekerServiceVo =
                new AlarmServiceVo(Constants.AlarmManager.AlarmManagerRequestCode.PEEK_USER_DATA,
                Constants.AlarmManager.PEEK_USER_DATA_DELAY_MILLISECONDS,
                Constants.AlarmManager.PEEK_USER_DATA_PERIODIC_TIME_MILLISECONDS);
        new UserDataAlarmManager(this, PointwiseSyncService.class).setUpAlarmManager(userDataPeekerServiceVo);
    }
}