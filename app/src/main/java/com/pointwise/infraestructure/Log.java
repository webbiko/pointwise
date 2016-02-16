package com.pointwise.infraestructure;

import com.pointwise.util.Constants.LogLevel;

/**
 * Created by wbatista on 2/11/16.
 */
public class Log {
    private ApplicationConfiguration applicationConfiguration;
    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    public LogLevel getLogLevel() {
        return LogLevel.valueOf(this.applicationConfiguration.getLogLevel());
    }

    public Boolean isTraceEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() >= LogLevel.TRACE.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean isDebugEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() >= LogLevel.DEBUG.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean isWarningEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() >= LogLevel.WARNING.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean isErrorEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() >= LogLevel.ERROR.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean isFatalEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() >= LogLevel.FATAL.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public Boolean isLogEnabled() {
        Boolean result = Boolean.FALSE;
        if (this.getLogLevel().getValue() > LogLevel.NONE.getValue()) {
            result = Boolean.TRUE;
        }
        return result;
    }

    public void logTrace(String tag, String logData) {
        if (this.isTraceEnabled()) {
            android.util.Log.i(tag, logData);
        }
    }

    public void logTrace(String tag, String logData, Exception exception) {
        if (this.isTraceEnabled()) {
            android.util.Log.i(tag, logData, exception);
        }
    }

    public void logDebug(String tag, String logData) {
        if (this.isDebugEnabled()) {
            android.util.Log.d(tag, logData);
        }
    }

    public void logDebug(String tag, String logData, Exception exception) {
        if (this.isDebugEnabled()) {
            android.util.Log.d(tag, logData, exception);
        }
    }

    public void logWarning(String tag, String logData) {
        if (this.isWarningEnabled()) {
            android.util.Log.w(tag, logData);
        }
    }

    public void logWarning(String tag, String logData, Exception exception) {
        if (this.isWarningEnabled()) {
            android.util.Log.w(tag, logData, exception);
        }
    }

    public void logError(String tag, String logData) {
        if (this.isErrorEnabled() && logData != null) {
            android.util.Log.e(tag, logData);
        }
    }

    public void logError(String tag, String logData, Exception exception) {
        if (this.isErrorEnabled()) {
            android.util.Log.e(tag, logData, exception);
        }
    }

    public void logFatal(String tag, String logData) {
        if (this.isFatalEnabled()) {
            android.util.Log.wtf(tag, logData);
        }
    }

    public void logFatal(String tag, String logData, Exception exception) {
        if (this.isFatalEnabled()) {
            android.util.Log.wtf(tag, logData, exception);
        }
    }
}