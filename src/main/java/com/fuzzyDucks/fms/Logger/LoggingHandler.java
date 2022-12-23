package com.fuzzyDucks.fms.Logger;


import java.util.logging.*;

public class LoggingHandler implements ILogger {
    private static ILogger instance = null;
    private static final Logger logger = Logger.getLogger(LoggingHandler.class.getSimpleName());

    private LoggingHandler() {
        try {
            logger.setLevel(Level.ALL);
            logger.setUseParentHandlers(false);
            FileHandler fh = new FileHandler("log.log", true);
            fh.setFormatter(new LoggerFormat());
            logger.addHandler(fh);
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
        logger.warning(message);
    }

    @Override
    public void logInfo(String message) {
        logger.info(message);

    }

    @Override
    public void logDebug(String message) {
        logger.fine(message);
    }
}
