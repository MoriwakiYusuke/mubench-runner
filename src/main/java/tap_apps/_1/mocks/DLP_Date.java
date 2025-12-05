package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.Util.DLP_Date
 */
public class DLP_Date {
    
    private long seconds;
    
    public DLP_Date() {
        this.seconds = System.currentTimeMillis() / 1000;
    }
    
    public DLP_Date(long seconds) {
        this.seconds = seconds;
    }
    
    public long convertToSeconds() {
        return seconds;
    }
    
    public static java.util.Calendar seconds2Calendar(long seconds) {
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.setTimeInMillis(seconds * 1000);
        return cal;
    }
    
    @Override
    public String toString() {
        return String.valueOf(seconds);
    }
}
