package com.ge.predix.labs.factories;

import java.util.HashMap;
import java.util.Map;
public class RequestContext {
    private static ThreadLocal<Map<String, Object>> localValueHolder =  new ThreadLocal<Map<String, Object>>() {
        @Override
        protected Map<String, Object> initialValue() {
            return new HashMap<>();
        }
    };

    @SuppressWarnings("unchecked")
	public static <T> T get(String key) {
        return (T) localValueHolder.get().get(key);
    }

    public static void put(String key, Object value) {
        localValueHolder.get().put(key, value);
    }

    public static Object remove(String key) {
        return localValueHolder.get().remove(key);
    }

    public static void clear() {
        localValueHolder.get().clear();
    }
}

