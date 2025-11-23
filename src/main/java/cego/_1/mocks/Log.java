package cego._1.mocks;

/**
 * Minimal log helper capturing the most recent error.
 */
public final class Log {

    private static String lastError;

    private Log() {
    }

    public static void e(String tag, String message) {
        lastError = tag + ": " + message;
    }

    public static String getLastError() {
        return lastError;
    }

    public static void reset() {
        lastError = null;
    }
}
