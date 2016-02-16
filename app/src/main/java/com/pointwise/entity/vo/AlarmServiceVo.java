package com.pointwise.entity.vo;

/**
 * Created by wbatista on 2/13/16.
 * This class is responsible for storing information about service
 * which will be setup in alarm manager
 */
public class AlarmServiceVo {

    private int alarmManagerRequestCode;

    private int delayBeforeStartingService;

    private int periodicTime;

    /**
     *
     * @param alarmManagerRequestCode
     * @param delayBeforeStartingService
     * @param periodicTime
     */
    public AlarmServiceVo(int alarmManagerRequestCode, int delayBeforeStartingService, int periodicTime) {
        this.alarmManagerRequestCode = alarmManagerRequestCode;
        this.delayBeforeStartingService = delayBeforeStartingService;
        this.periodicTime = periodicTime;
    }

    /**
     * Default constructor
     */
    public AlarmServiceVo() {

    }

    public int getAlarmManagerRequestCode() {
        return alarmManagerRequestCode;
    }

    public void setAlarmManagerRequestCode(int alarmManagerRequestCode) {
        this.alarmManagerRequestCode = alarmManagerRequestCode;
    }

    public int getDelayBeforeStartingService() {
        return delayBeforeStartingService;
    }

    public void setDelayBeforeStartingService(int delayBeforeStartingService) {
        this.delayBeforeStartingService = delayBeforeStartingService;
    }

    public int getPeriodicTime() {
        return periodicTime;
    }

    public void setPeriodicTime(int periodicTime) {
        this.periodicTime = periodicTime;
    }
}