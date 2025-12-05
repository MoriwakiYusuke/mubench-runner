package tap_apps._1.mocks;

import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

/**
 * Mock for org.jSyncManager.Conduit.NSMobileMessenger.NSMobileAuthenticator
 */
public class NSMobileAuthenticator implements AuthenticatorInterface {
    
    @Override
    public InputStream authenticateAndGetReader(URL url, String username, String password, HashMap<String, String> cookieMap) throws Exception {
        // Mock implementation - returns null
        return null;
    }
}
