package gnucrasha._1a.mocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Minimal bundle storing key-value pairs.
 */
public class Bundle {

    private final Map<String, Object> values = new HashMap<>();

    public void putString(String key, String value) {
        values.put(key, value);
    }

    public String getString(String key) {
        Object value = values.get(key);
        return value instanceof String ? (String) value : null;
    }
}
