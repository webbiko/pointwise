package com.pointwise.util;

/**
 * Created by wbatista on 2/11/16.
 */
public class Constants {

    public static final String LOG_TAG = "Pointwise";

    public static final String BUNDLE_USER_DATA = "BUNDLE_USER_DATA";

    public static final String BUNDLE_QUEUE_SIZE = "BUNDLE_QUEUE_SIZE";

    public enum ApplicationConfigurationMode {
        DEVELOPMENT, STAGE, RELEASE
    }

    public static final class Parameters {

        public static final String SYNCHRONIZATION_TIME = "SYNCHRONIZATION_TIME";
        public static final String DATA_COLLECTOR_TIME = "DATA_COLLECTOR_TIME";
        public static final String LOG_LEVEL = "LOG_LEVEL";
    }

    public enum LogLevel {
        TRACE(5), DEBUG(4), WARNING(3), ERROR(2), FATAL(1), NONE(0);

        private final int logLevelId;

        LogLevel(int logLevelId) {
            this.logLevelId = logLevelId;
        }

        public int getValue() {
            return this.logLevelId;
        }
    }

    public static final class ErrorCodes {

        // Internal errors only
        public static final class Application {
            // Error related to null operation result
            public static final String ERROR_CODE_NULL_RESULT = "PV0001";
        }
    }

    public static final class Service {

        public static final String COMMAND_CODE_KEY = "COMMAND_CODE_KEY";
        public static final String RESULT_RECEIVER_KEY = "RESULT_RECEIVER_KEY";
        public static final String ARGUMENTS_KEY = "ARGUMENTS_KEY";

        public static final class CommandCode {

            public static final int COLLECT_USER_DATA = 1;
            public static final int PEEK_USER_DATA = 2;
        }

        public static final class ResultReceiverCode {

            public static final int SUCCESS = 0;
            public static final int FAILURE = 1;
            public static final int RETRY = 2;
            public static final int CANCEL = 3;
        }
    }

    public interface AlarmManager {

        // The values below should be parametirized in a web portal and consumed by modile
        int COLLECT_USER_DATA_DELAY_MILLISECONDS = 1000 * 20;
        int COLLECT_USER_DATA_PERIODIC_TIME_MILLISECONDS = 1000 * 10;

        int PEEK_USER_DATA_DELAY_MILLISECONDS = 1000 * 40;
        int PEEK_USER_DATA_PERIODIC_TIME_MILLISECONDS = 1000 * 15;

        interface AlarmManagerRequestCode {
            int COLLECT_USER_DATA = 10;
            int PEEK_USER_DATA = 20;
        }
    }

    public static final class BroadcastEvents {
        public static final String USER_DATA_PICKED = "USER_DATA_PICKED";
        public static final String UNRELIABLE_NETWORK = "UNRELIABLE_NETWORK";
    }
}