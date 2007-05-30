package org.shiftone.jrat.util.log;


/**
 * @author Jeff Drost
 */
public interface Constants {

    public static final int LEVEL_TRACE = 0;
    public static final int LEVEL_DEBUG = 1;
    public static final int LEVEL_INFO = 2;
    public static final int LEVEL_WARN = 3;
    public static final int LEVEL_ERROR = 4;
    public static final int LEVEL_FATAL = 5;
    public static final int DEFAULT_LEVEL = LEVEL_INFO;
    public static final String[] LEVEL_NAMES =
            {
                    "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL"
            };
}
