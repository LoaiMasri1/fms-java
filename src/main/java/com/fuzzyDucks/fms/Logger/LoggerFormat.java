package com.fuzzyDucks.fms.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LoggerFormat extends Formatter {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss a");

    @Override
    public String format(LogRecord record) {
        String loggerName = record.getLoggerName();
        String level = record.getLevel().getName();
        String message = record.getMessage();
        long timeStamp = record.getMillis();
        String dateString = DATE_FORMAT.format(new Date(timeStamp));

        return String.format("[%s] %s %s %s%n", level, dateString, loggerName, message);
    }
}
