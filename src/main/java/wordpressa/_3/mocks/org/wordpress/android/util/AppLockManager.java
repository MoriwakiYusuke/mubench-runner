package wordpressa._3.mocks.org.wordpress.android.util;

public class AppLockManager {
    private static AppLockManager instance = new AppLockManager();
    
    public static AppLockManager getInstance() { return instance; }
    
    public void setExtendedTimeout() {}
    public void setLocked(boolean locked) {}
    public boolean isLocked() { return false; }
}
