package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.Conduit.NSMobileOwnerDBConduit.MessageOwnerRecord
 */
public class MessageOwnerRecord {
    
    private int userID;
    private byte[] encryptionKey;
    
    public MessageOwnerRecord() {
    }
    
    public MessageOwnerRecord(int userID, byte[] encryptionKey) {
        this.userID = userID;
        this.encryptionKey = encryptionKey;
    }
    
    public int getUserID() {
        return userID;
    }
    
    public byte[] getEncryptionKey() {
        return encryptionKey;
    }
}
