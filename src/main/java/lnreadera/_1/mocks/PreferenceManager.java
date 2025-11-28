package lnreadera._1.mocks;

public class PreferenceManager {
    private static SharedPreferences prefs = new SharedPreferences();

    public static SharedPreferences getDefaultSharedPreferences(Object context) {
        return prefs;
    }

    public static void reset() {
        prefs = new SharedPreferences();
    }
}
