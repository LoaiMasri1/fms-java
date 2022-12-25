package com.fuzzyDucks.fms.Logger;


import com.fuzzyDucks.fms.Logger.intf.ILogger;

import java.util.logging.*;

public class LoggingHandler implements ILogger {
    private static ILogger instance = null;
    private static final Logger logger = Logger.getLogger(LoggingHandler.class.getSimpleName());

    private LoggingHandler() {
        try {
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            FileHandler fileHandler = new FileHandler("log.log",true);
            fileHandler.setFormatter(new LoggerFormat());
            logger.addHandler(fileHandler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ILogger getInstance() {
        if (instance == null) {
            instance = new LoggingHandler();
        }
        return instance;
    }

    @Override
    public void logWarning(String message) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        logger.warning(String.format("[%s.%s] %s", className, methodName, message));
    }

    @Override
    public void logInfo(String message) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        logger.info(String.format("[%s.%s] %s", className, methodName, message));
    }

    @Override
    public void logDebug(String message) {
        StackTraceElement caller = Thread.currentThread().getStackTrace()[2];
        String className = caller.getClassName();
        String methodName = caller.getMethodName();
        logger.fine(String.format("[%s.%s] %s", className, methodName, message));
    }
}
