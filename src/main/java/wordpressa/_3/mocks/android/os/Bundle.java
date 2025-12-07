package wordpressa._3.mocks.android.os;

import java.util.HashMap;
import java.util.Map;

public class Bundle {
    private Map<String, Object> data = new HashMap<>();
    
    public void putString(String key, String value) { data.put(key, value); }
    public String getString(String key) { return (String) data.get(key); }
    public String getString(String key, String defaultValue) { 
        String v = getString(key);
        return v != null ? v : defaultValue;
    }
    
    public void putInt(String key, int value) { data.put(key, value); }
    public int getInt(String key) { return getInt(key, 0); }
    public int getInt(String key, int defaultValue) { 
        Integer v = (Integer) data.get(key);
        return v != null ? v : defaultValue;
    }
    
    public void putBoolean(String key, boolean value) { data.put(key, value); }
    public boolean getBoolean(String key) { return getBoolean(key, false); }
    public boolean getBoolean(String key, boolean defaultValue) { 
        Boolean v = (Boolean) data.get(key);
        return v != null ? v : defaultValue;
    }
    
    public void putParcelable(String key, Object value) { data.put(key, value); }
    public Object getParcelable(String key) { return data.get(key); }
    
    public void putSerializable(String key, java.io.Serializable value) { data.put(key, value); }
    public java.io.Serializable getSerializable(String key) { return (java.io.Serializable) data.get(key); }
    
    public boolean containsKey(String key) { return data.containsKey(key); }
    public void clear() { data.clear(); }
}
