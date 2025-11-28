package jriecken_gae_java_mini_profiler._39.mocks;

/**
 * Mock for com.google.appengine.api.memcache.MemcacheServiceFactory
 */
public class MemcacheServiceFactory {
    
    public static MemcacheService getMemcacheService(String namespace) {
        return new MockMemcacheService();
    }
    
    private static class MockMemcacheService implements MemcacheService {
        // Empty implementation
    }
}
