package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Conduit.AuthenticatorInterface
 */
public interface AuthenticatorInterface {
    
    java.io.InputStream authenticateAndGetReader(java.net.URL url, String username, String password, java.util.HashMap<String, String> cookieMap) throws Exception;
}
