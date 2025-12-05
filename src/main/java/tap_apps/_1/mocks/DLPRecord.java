package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.Util.DLPRecord
 */
public class DLPRecord {
    
    public static final byte DELETE_ALL_IN_CATEGORY = (byte) 0x80;
    
    protected int recordID;
    
    public int getRecordID() {
        return recordID;
    }
    
    public void setRecordID(int id) {
        this.recordID = id;
    }
    
    public byte[] toByteArray() {
        return new byte[0];
    }
}
