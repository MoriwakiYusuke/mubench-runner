package wordpressa._3.mocks.org.wordpress.passcodelock;

public class AppLockManager {
    private static AppLockManager instance = new AppLockManager();
    private boolean appLockEnabled = false;
    
    public static AppLockManager getInstance() { return instance; }
    
    public boolean isAppLockFeatureEnabled() { return appLockEnabled; }
    public void setAppLockFeatureEnabled(boolean enabled) { this.appLockEnabled = enabled; }
    
    public void setExtendedTimeout() {}
    
    public AbstractAppLock getAppLock() { return new AbstractAppLock(); }
    
    public static class AbstractAppLock {
        public void setExemptActivities(String[] activities) {}
        public void setOneTimeTimeout(int timeout) {}
    }
}
