package jriecken_gae_java_mini_profiler._39.mocks;

/**
 * Mock for com.google.appengine.tools.appstats.MemcacheWriter
 */
public class MemcacheWriter {
    
    public MemcacheWriter(Object config, MemcacheService memcacheService) {
        // Mock constructor - parameters ignored
    }
    
    public StatsProtos.RequestStatProto getFull(long id) {
        // Return a mock RequestStatProto for testing
        return new StatsProtos.RequestStatProto();
    }
}
