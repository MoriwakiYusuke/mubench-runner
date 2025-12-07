package wordpressa._1.mocks;

/**
 * Mock for android.app.Activity
 */
public class Activity extends Context {
    public View findViewById(int id) {
        return new View();
    }
}
