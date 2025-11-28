package lnreadera._2.mocks;

public class LNReaderApplication {
    private static final LNReaderApplication INSTANCE = new LNReaderApplication();

    public static LNReaderApplication getInstance() {
        return INSTANCE;
    }

    public boolean addTask(String key, Object task) {
        return false; // Don't actually run tasks in test
    }
}
