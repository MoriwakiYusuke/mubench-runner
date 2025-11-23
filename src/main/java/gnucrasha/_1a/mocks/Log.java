package gnucrasha._1a.mocks;

/**
 * Minimal Log implementation storing the last debug message.
 */
public final class Log {

    private static String lastDebug;

    private Log() {
    }

    public static void d(String tag, String message) {
        lastDebug = tag + ": " + message;
    }

    public static String getLastDebug() {
        return lastDebug;
    }
}
