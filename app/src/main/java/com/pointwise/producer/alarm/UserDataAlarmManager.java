package com.pointwise.producer.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;

import com.pointwise.entity.vo.AlarmServiceVo;
import com.pointwise.util.Constants;


/**
 * Created by wbatista on 2/11/16.
 * This class is responsible for starting periodic tasks in order to peek and generate user data
 */
public class UserDataAlarmManager {

    private AlarmManager mAlarmManager;

    private PendingIntent mUserDataIntent;

    private Context mContext;

    private Intent mIntent;

    public UserDataAlarmManager(final Context context, final Class<? extends Service> serviceClass) {
        this.mContext = context;
        this.mAlarmManager = (AlarmManager) this.mContext.getSystemService(Context.ALARM_SERVICE);
        this.mIntent = new Intent(this.mContext, serviceClass);
    }

    public void setUpAlarmManager(final AlarmServiceVo alarmServiceVo) {
        switch (alarmServiceVo.getAlarmManagerRequestCode()) {
            case Constants.AlarmManager.AlarmManagerRequestCode.COLLECT_USER_DATA: {
                this.mIntent.putExtra(Constants.Service.COMMAND_CODE_KEY, Constants.Service.CommandCode.COLLECT_USER_DATA);
            } break;
            case Constants.AlarmManager.AlarmManagerRequestCode.PEEK_USER_DATA: {
                this.mIntent.putExtra(Constants.Service.COMMAND_CODE_KEY, Constants.Service.CommandCode.PEEK_USER_DATA);
            } break;
            default: {
                throw new IllegalArgumentException("Invalid alarm service");
            }
        }

        boolean alarmUp = (PendingIntent.getService(mContext, 0, this.mIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

        if(!alarmUp) {
            mUserDataIntent = PendingIntent.getService(this.mContext, 0, this.mIntent, 0);
            mAlarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    SystemClock.elapsedRealtime() + alarmServiceVo.getDelayBeforeStartingService(),
                    alarmServiceVo.getPeriodicTime(), mUserDataIntent);
        }
    }

    public void stopCllectingData() {
        // If the alarm has been set, cancel it.
        if (mAlarmManager != null) {
            mAlarmManager.cancel(mUserDataIntent);
        }
    }
}