package com.fuzzyDucks.fms.Cache;

import com.fuzzyDucks.fms.Logger.ILogger;
import com.fuzzyDucks.fms.Logger.LoggingHandler;

import java.util.HashMap;

public class Cache {
    private static Cache instance = null;
    private static final HashMap<String, Object> cache = new HashMap<>();
    private static final ILogger logger= LoggingHandler.getInstance();
    private Cache() {
    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
            logger.logInfo("Cache instance created");
        }
        return instance;
    }

    public void put(String key, Object value) {
        cache.put(key, value);
    }

    public Object get(String key) {
        return cache.get(key);
    }

}
