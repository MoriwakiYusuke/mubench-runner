package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Conduit.ConduitHandlerException
 */
public class ConduitHandlerException extends Exception {
    
    private DLPException dlpException;
    
    public ConduitHandlerException(String message, DLPException dlpException) {
        super(message);
        this.dlpException = dlpException;
    }
    
    public DLPException getDlpException() {
        return dlpException;
    }
}
