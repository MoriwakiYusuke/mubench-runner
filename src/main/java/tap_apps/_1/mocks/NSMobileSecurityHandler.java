package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.SecurityImpl.NSMobileSecurityHandler
 */
public class NSMobileSecurityHandler {
    
    public static final String MESSAGE_HEADER_URL = "messageHeaderUrl";
    public static final String MESSAGE_DATA_URL = "messageDataUrl";
    public static final String MESSAGE_WRITE_URL = "messageWriteUrl";
    public static final String MESSAGE_USER_NAME = "messageUserName";
    public static final String MESSAGE_AUTH_URL = "authUrl";
    public static final String PLONE_USERNAME = "ploneUsername";
    public static final String PLONE_PASSWORD = "plonePassword";
    public static final String UNSET_VALUE = "";
    
    public static String getUserPermissionFile(String userName) {
        return System.getProperty("user.home") + "/NSMobile/" + userName + ".properties";
    }
}
