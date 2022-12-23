package com.fuzzyDucks.fms.Logger;

public interface ILogger {
    void logWarning(String message);
    void logInfo(String message);
    void logDebug(String message);
}
