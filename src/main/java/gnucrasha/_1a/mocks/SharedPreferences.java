package gnucrasha._1a.mocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Simplified SharedPreferences backed by a map.
 */
public class SharedPreferences {

    private final Map<String, Object> values = new HashMap<>();

    public boolean getBoolean(String key, boolean defaultValue) {
        Object value = values.get(key);
        return value instanceof Boolean ? (Boolean) value : defaultValue;
    }

    public String getString(String key, String defaultValue) {
        Object value = values.get(key);
        return value instanceof String ? (String) value : defaultValue;
    }

    public long getLong(String key, long defaultValue) {
        Object value = values.get(key);
        return value instanceof Number ? ((Number) value).longValue() : defaultValue;
    }

    public Editor edit() {
        return new Editor();
    }

    public final class Editor {

        private final Map<String, Object> staged = new HashMap<>();

        public Editor putBoolean(String key, boolean value) {
            staged.put(key, value);
            return this;
        }

        public Editor putString(String key, String value) {
            staged.put(key, value);
            return this;
        }

        public Editor putLong(String key, long value) {
            staged.put(key, value);
            return this;
        }

        public void apply() {
            commit();
        }

        public boolean commit() {
            values.putAll(staged);
            staged.clear();
            return true;
        }
    }
}
