package tucanmobile._1.mocks;

/**
 * Mock for android.content.res.Resources - stub implementation for testing.
 */
public class Resources {
    public String getString(int resId) {
        return "mock_string_" + resId;
    }
}
