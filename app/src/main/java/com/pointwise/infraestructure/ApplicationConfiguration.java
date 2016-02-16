package com.pointwise.infraestructure;

import com.pointwise.util.Constants;
import com.pointwise.util.Constants.ApplicationConfigurationMode;
import com.pointwise.util.Constants.LogLevel;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wbatista on 2/11/16.
 */
public class ApplicationConfiguration {

    private final ApplicationConfigurationMode applicationConfigurationMode;
    private static final Map<Integer, Map<String, String>> applicationParameters;

    static {
        Map<Integer, Map<String, String>> parameterMap = new HashMap<>();

        // DEVELOPMENT Parameters
        parameterMap.put(ApplicationConfigurationMode.DEVELOPMENT.ordinal(), new HashMap<String, String>());
        Map<String, String> development = parameterMap.get(ApplicationConfigurationMode.DEVELOPMENT.ordinal());
        development.put(Constants.Parameters.SYNCHRONIZATION_TIME, "");
        development.put(Constants.Parameters.DATA_COLLECTOR_TIME, "");
        development.put(Constants.Parameters.LOG_LEVEL, LogLevel.DEBUG.name());

        // STAGE (DEFAULT) Parameters
        parameterMap.put(ApplicationConfigurationMode.STAGE.ordinal(), new HashMap<String, String>());
        Map<String, String> stageDefault = parameterMap.get(ApplicationConfigurationMode.STAGE.ordinal());
        stageDefault.put(Constants.Parameters.SYNCHRONIZATION_TIME, "");
        stageDefault.put(Constants.Parameters.DATA_COLLECTOR_TIME, "");
        stageDefault.put(Constants.Parameters.LOG_LEVEL, LogLevel.DEBUG.name());

        // RELEASE Parameters
        parameterMap.put(ApplicationConfigurationMode.RELEASE.ordinal(), new HashMap<String, String>());
        Map<String, String> release = parameterMap.get(ApplicationConfigurationMode.RELEASE.ordinal());
        release.put(Constants.Parameters.SYNCHRONIZATION_TIME, "");
        release.put(Constants.Parameters.DATA_COLLECTOR_TIME, "");
        release.put(Constants.Parameters.LOG_LEVEL, LogLevel.NONE.name());

        applicationParameters = Collections.unmodifiableMap(parameterMap);
    }

    public ApplicationConfiguration() {
        this.applicationConfigurationMode = ApplicationConfigurationMode.DEVELOPMENT;
    }

    public ApplicationConfiguration(ApplicationConfigurationMode applicationConfigurationMode) {
        this.applicationConfigurationMode = applicationConfigurationMode;
    }

    public ApplicationConfigurationMode getApplicationConfigurationMode() {
        return this.applicationConfigurationMode;
    }

    public String getSynchronizationTime() {
        return applicationParameters.get(this.applicationConfigurationMode.ordinal()).get(
                Constants.Parameters.SYNCHRONIZATION_TIME);
    }

    public String getDataColletorTime() {
        return applicationParameters.get(this.applicationConfigurationMode.ordinal()).get(
                Constants.Parameters.DATA_COLLECTOR_TIME);
    }

    public String getLogLevel() {
        return applicationParameters.get(this.applicationConfigurationMode.ordinal()).get(
                Constants.Parameters.LOG_LEVEL);
    }
}