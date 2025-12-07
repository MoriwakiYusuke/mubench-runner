package wordpressa._1.mocks;

/**
 * Mock for android.content.Context
 */
public class Context {
    public void runOnUiThread(Runnable action) {
        // Execute immediately for testing
        action.run();
    }
}
