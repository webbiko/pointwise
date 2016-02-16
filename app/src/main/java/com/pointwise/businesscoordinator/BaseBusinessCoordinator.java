package com.pointwise.businesscoordinator;

import com.pointwise.infraestructure.ApplicationConfiguration;
import com.pointwise.infraestructure.Log;

/**
 * Created by wbatista on 2/12/16.
 */
public abstract class BaseBusinessCoordinator {

    protected Log log;

    protected ApplicationConfiguration applicationConfiguration;

    public void setLog(Log log) {
        this.log = log;
    }

    public void setApplicationConfiguration(ApplicationConfiguration applicationConfiguration) {
        this.applicationConfiguration = applicationConfiguration;
    }
}
