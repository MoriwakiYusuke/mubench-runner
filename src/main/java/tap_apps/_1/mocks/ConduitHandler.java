package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Conduit.ConduitHandler
 */
public class ConduitHandler {
    
    public void postToLog(String message) {
        // Mock implementation
    }
    
    public Object getProperty(String key) {
        return null;
    }
    
    public DLPFindDBResponse findDatabaseByName(byte arg0, byte arg1, String name) throws ConduitHandlerException {
        throw new ConduitHandlerException("Mock", new DLPException(0));
    }
    
    public byte openDatabase(String name, byte mode) throws ConduitHandlerException {
        return 0;
    }
    
    public byte createDatabase(String creatorId, String type, String name, char arg3, char arg4) throws ConduitHandlerException {
        return 0;
    }
    
    public byte[] getApplicationBlock(byte dbId) throws ConduitHandlerException {
        return new byte[0];
    }
    
    public void resetRecordIndex(byte dbId) throws ConduitHandlerException {
        // Mock implementation
    }
    
    public byte[] readNextRecordInCategory(byte dbId, int category) throws ConduitHandlerException {
        throw new ConduitHandlerException("No more records", new DLPException(DLP_Packet.ERR_NOT_FOUND));
    }
    
    public int writeRecord(byte dbId, byte flags, DLPRecord record) throws ConduitHandlerException {
        return 0;
    }
    
    public void deleteRecord(byte dbId, byte flags, int recordId) throws ConduitHandlerException {
        // Mock implementation
    }
    
    public void cleanupDatabase(byte dbId) throws ConduitHandlerException {
        // Mock implementation
    }
    
    public void resetSyncFlags(byte dbId) throws ConduitHandlerException {
        // Mock implementation
    }
    
    public void writeApplicationBlock(byte dbId, DLPRecord appBlock) throws ConduitHandlerException {
        // Mock implementation
    }
    
    public void closeDatabase(byte dbId) throws ConduitHandlerException {
        // Mock implementation
    }
}
