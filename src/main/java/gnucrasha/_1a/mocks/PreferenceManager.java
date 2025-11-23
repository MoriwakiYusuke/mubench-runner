package gnucrasha._1a.mocks;

/**
 * Provides a single global SharedPreferences instance for tests.
 */
public final class PreferenceManager {

    private static SharedPreferences defaultSharedPreferences = new SharedPreferences();

    private PreferenceManager() {
    }

    public static SharedPreferences getDefaultSharedPreferences(Context context) {
        return defaultSharedPreferences;
    }

    public static void reset() {
        defaultSharedPreferences = new SharedPreferences();
    }
}
