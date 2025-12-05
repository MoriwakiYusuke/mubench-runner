package tap_apps._1.mocks;

import javax.swing.JPanel;

/**
 * Mock for org.jSyncManager.API.Conduit.AbstractConduit
 */
public abstract class AbstractConduit {
    
    protected abstract String getResourceBundleName();
    
    public abstract byte getPriority();
    
    public abstract void startSync(ConduitHandler ch, DLPUserInfo user) throws NotConnectedException;
    
    protected abstract JPanel constructConfigPanel();
    
    protected abstract void doInitialization();
}
