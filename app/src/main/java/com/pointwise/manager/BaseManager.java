package com.pointwise.manager;

import android.os.AsyncTask;

import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wbatista on 2/12/16.
 */
public abstract class BaseManager {
    /**
     * log and applicationConfiguration (analytics may also be applied to this class)
     * will be used for all managers
     */
    protected Log log;
    protected ApplicationConfiguration applicationConfiguration;

    /**
     * taskList e mContext
     * Needed attributes for managing async tasks
     */
    private List<AsyncTask<?, ?, ?>> taskList = null;

    /**
     * Default constructor
     */
    protected BaseManager() {
        taskList = new ArrayList<>();
    }

    /**
     * Log class
     * @param log Log
     */
    public void setLog(Log log) {
        this.log = log;
    }

    /**
     * Application configuration may be needed in some tasks
     * @param applicationConfiguration ApplicationConfiguration
     */
    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }

    /**
     * Cancel all async tasks.
     * Most of time used in onDestroy methods from Activity lifecycle
     */
    public void cancelOperations() {
        if(taskList != null && !taskList.isEmpty()) {
            for (AsyncTask<?, ?, ?> task : taskList) {
                task.cancel(true);
            }
        }
    }

    /**
     * Add a new AsyncTask to list
     * @param task AsyncTask<?, ?, ?>
     */
    protected void addToTaskList(AsyncTask<?, ?, ?> task) {
        taskList.add(task);
    }

    /**
     * Remove Asynctask from list
     * @param task AsyncTask<?, ?, ?>
     */
    protected void removeFromTaskList(AsyncTask<?, ?, ?> task) {
        taskList.remove(task);
    }
}
