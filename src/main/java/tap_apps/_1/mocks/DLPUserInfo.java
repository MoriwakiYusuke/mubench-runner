package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.Util.DLPUserInfo
 */
public class DLPUserInfo {
    
    private String userName;
    private int userID;
    
    public DLPUserInfo() {
    }
    
    public DLPUserInfo(String userName, int userID) {
        this.userName = userName;
        this.userID = userID;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public int getUserID() {
        return userID;
    }
}
