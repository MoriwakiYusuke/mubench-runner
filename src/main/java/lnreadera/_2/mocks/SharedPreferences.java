package lnreadera._2.mocks;

public class SharedPreferences {
    public boolean getBoolean(String key, boolean defValue) { return defValue; }
    public String getString(String key, String defValue) { return defValue; }
    public Editor edit() { return new Editor(); }

    public static class Editor {
        public Editor putBoolean(String key, boolean value) { return this; }
        public Editor putString(String key, String value) { return this; }
        public void commit() {}
    }
}
