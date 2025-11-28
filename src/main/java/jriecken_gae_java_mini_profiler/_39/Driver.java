package jriecken_gae_java_mini_profiler._39;

import java.util.Map;

/**
 * Driver class for testing MiniProfilerAppstats variants.
 * Tests the getAppstatsDataFor method with various inputs to verify
 * proper handling of NumberFormatException.
 */
public class Driver {
    
    private final String targetClassName;
    
    /**
     * Constructor that takes the fully qualified class name of the target variant.
     * 
     * @param targetClassName e.g., "jriecken_gae_java_mini_profiler._39.original.MiniProfilerAppstats"
     */
    public Driver(String targetClassName) {
        this.targetClassName = targetClassName;
    }
    
    /**
     * Invokes getAppstatsDataFor method on the target class using reflection.
     * 
     * @param appstatsId The appstats ID (may be invalid to test exception handling)
     * @param maxStackFrames Maximum stack frames
     * @return The result map, or null if an exception occurred
     * @throws Exception if reflection fails or NumberFormatException is not caught
     */
    @SuppressWarnings("unchecked")
    public Map<String, Object> getAppstatsDataFor(String appstatsId, Integer maxStackFrames) throws Exception {
        Class<?> clazz = Class.forName(targetClassName);
        java.lang.reflect.Method method = clazz.getMethod("getAppstatsDataFor", String.class, Integer.class);
        return (Map<String, Object>) method.invoke(null, appstatsId, maxStackFrames);
    }
    
    /**
     * Tests if the implementation properly handles invalid (non-numeric) appstatsId
     * by not throwing NumberFormatException.
     * 
     * @param invalidId An invalid (non-numeric) ID string
     * @return true if NumberFormatException is properly caught, false if it propagates
     */
    public boolean handlesInvalidIdGracefully(String invalidId) {
        try {
            getAppstatsDataFor(invalidId, 10);
            // If we reach here, exception was caught internally
            return true;
        } catch (Exception e) {
            // Check if it's a NumberFormatException (wrapped in InvocationTargetException)
            Throwable cause = e.getCause();
            if (cause instanceof NumberFormatException) {
                return false; // NumberFormatException was NOT handled
            }
            // Other exceptions are unexpected
            return true;
        }
    }
    
    /**
     * Tests if the implementation works correctly with a valid numeric ID.
     * 
     * @param validId A valid numeric ID string
     * @return true if the method executes successfully
     */
    public boolean handlesValidIdCorrectly(String validId) {
        try {
            Map<String, Object> result = getAppstatsDataFor(validId, 10);
            // Should return a non-null map with valid ID
            return result != null;
        } catch (Exception e) {
            return false;
        }
    }
}
