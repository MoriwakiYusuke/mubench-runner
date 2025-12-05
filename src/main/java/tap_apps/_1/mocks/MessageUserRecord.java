package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.Conduit.NSMobileUsersDBConduit.MessageUserRecord
 */
public class MessageUserRecord {
    
    private int userID;
    private String userName;
    
    public MessageUserRecord() {
    }
    
    public MessageUserRecord(int userID, String userName) {
        this.userID = userID;
        this.userName = userName;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public String getUserName() {
        return userName;
    }
}
