package wordpressa._1.mocks;

import java.util.HashMap;
import java.util.Map;

/**
 * Mock for android.os.Bundle
 */
public class Bundle {
    private Map<String, Object> data = new HashMap<>();
    private boolean empty = true;

    public boolean isEmpty() {
        return empty;
    }

    public void putBoolean(String key, boolean value) {
        data.put(key, value);
        empty = false;
    }

    public boolean getBoolean(String key) {
        return data.containsKey(key) ? (Boolean) data.get(key) : false;
    }
}
