package wordpressa._3.mocks.org.wordpress.android.util;

public class DeviceUtils {
    private static DeviceUtils instance = new DeviceUtils();
    
    public static DeviceUtils getInstance() { return instance; }
    
    public boolean hasCamera(Object context) { return true; }
    
    public static boolean isSmallTablet(Object context) { return false; }
    public static boolean isLargeTablet(Object context) { return false; }
    public static boolean isTablet(Object context) { return false; }
    public static boolean isPhone(Object context) { return true; }
    public static boolean isXLargeTablet(Object context) { return false; }
}
