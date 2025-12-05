package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.Util.DLPFindDBResponse
 */
public class DLPFindDBResponse {
    
    private String databaseName;
    
    public DLPFindDBResponse() {
    }
    
    public DLPFindDBResponse(String name) {
        this.databaseName = name;
    }
    
    public String getDatabaseName() {
        return databaseName;
    }
}
