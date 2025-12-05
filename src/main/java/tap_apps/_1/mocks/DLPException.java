package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.DLPException
 */
public class DLPException extends Exception {
    
    private int errorCode;
    
    public DLPException(int errorCode) {
        this.errorCode = errorCode;
    }
    
    public int getErrorCode() {
        return errorCode;
    }
}
