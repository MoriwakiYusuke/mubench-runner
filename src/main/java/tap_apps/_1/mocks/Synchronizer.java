package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Threads.Synchronizer
 */
public class Synchronizer {
    
    private static String homeDirectory = System.getProperty("user.home");
    
    public static String getHomeDirectory() {
        return homeDirectory;
    }
    
    public static void setHomeDirectory(String dir) {
        homeDirectory = dir;
    }
}
