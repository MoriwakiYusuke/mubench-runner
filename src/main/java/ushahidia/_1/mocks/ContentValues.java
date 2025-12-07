package ushahidia._1.mocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock implementation of android.content.ContentValues for testing.
 */
public class ContentValues {
    private final Map<String, Object> values = new HashMap<>();

    public void put(String key, String value) {
        values.put(key, value);
    }

    public void put(String key, Long value) {
        values.put(key, value);
    }

    public void put(String key, Integer value) {
        values.put(key, value);
    }

    public Object get(String key) {
        return values.get(key);
    }
}
