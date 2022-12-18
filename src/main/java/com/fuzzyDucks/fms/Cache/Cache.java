package com.fuzzyDucks.fms.Cache;

import java.util.HashMap;

public class Cache {
    private static Cache instance = null;
    private static final HashMap<String, Object> cache = new HashMap<String, Object>();

    private Cache() {
    }

    public static Cache getInstance() {
        if (instance == null) {
            instance = new Cache();
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
