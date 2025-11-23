package gnucrasha._1b.mocks;

/**
 * Holds global session timing fields.
 */
public final class GnuCashApplication {

    public static long PASSCODE_SESSION_INIT_TIME = 0L;
    public static long SESSION_TIMEOUT = 5 * 60 * 1000L;

    private GnuCashApplication() {
    }

    public static void reset() {
        PASSCODE_SESSION_INIT_TIME = 0L;
    }
}
