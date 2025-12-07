package wordpressa._1.mocks;

/**
 * Mock for android.os.Handler
 */
public class Handler {
    public boolean postDelayed(Runnable r, long delayMillis) {
        // Execute immediately for testing
        r.run();
        return true;
    }
}
