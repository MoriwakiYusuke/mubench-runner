package testng._22;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Driver for TestNG Case 22 - XMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over results map.
 * 
 * Supports both static analysis and dynamic testing.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._22";
    private final String variant;
    private Object reporterInstance;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_22/" + variant + "/XMLReporter.java");
        return Files.readString(path);
    }
    
    // ========== Dynamic Testing Methods ==========
    
    /**
     * Initialize XMLReporter instance for dynamic testing.
     */
    public void initializeReporter() throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".XMLReporter";
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        this.reporterInstance = constructor.newInstance();
    }
    
    /**
     * Invoke getSuiteAttributes method dynamically (private method).
     */
    public Properties invokeGetSuiteAttributes(Object suite) throws Exception {
        if (reporterInstance == null) {
            initializeReporter();
        }
        // Use getDeclaredMethod for private method
        Method method = reporterInstance.getClass().getDeclaredMethod("getSuiteAttributes", Object.class);
        method.setAccessible(true);
        return (Properties) method.invoke(reporterInstance, suite);
    }
    
    /**
     * Test concurrent access to verify synchronization behavior.
     */
    public boolean testConcurrentAccess(int threadCount) throws Exception {
        if (reporterInstance == null) {
            initializeReporter();
        }
        
        final boolean[] success = {true};
        Thread[] threads = new Thread[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        invokeGetSuiteAttributes(new Object());
                    }
                } catch (Exception e) {
                    if (e.getCause() instanceof java.util.ConcurrentModificationException) {
                        success[0] = false;
                    }
                }
            });
        }
        
        for (Thread t : threads) t.start();
        for (Thread t : threads) t.join();
        
        return success[0];
    }
    
    // ========== Static Analysis Methods ==========
    
    /**
     * Check if getSuiteAttributes method exists
     */
    public boolean hasGetSuiteAttributesMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("getSuiteAttributes") || source.contains("Properties getSuiteAttributes");
    }
    
    /**
     * Check if results field/variable exists
     */
    public boolean hasResultsVariable() throws IOException {
        String source = readSourceCode();
        return source.contains("results") && source.contains("getResults(");
    }
    
    /**
     * Check if iteration over results exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("results.entrySet()") || 
               source.contains("for (Map.Entry") ||
               source.contains("for(Map.Entry");
    }
    
    /**
     * Check if synchronized block is present for results iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (results)") || 
               source.contains("synchronized(results)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (results) { ... results.entrySet() ... }
        int syncIndex = source.indexOf("synchronized (results)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(results)");
        }
        if (syncIndex == -1) {
            return false;
        }
        
        // Find the opening brace after synchronized
        int braceStart = source.indexOf("{", syncIndex);
        if (braceStart == -1) {
            return false;
        }
        
        // Find matching closing brace
        int braceCount = 1;
        int braceEnd = braceStart + 1;
        while (braceEnd < source.length() && braceCount > 0) {
            char c = source.charAt(braceEnd);
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            braceEnd++;
        }
        
        // Check if iteration over results is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("results.entrySet()") || syncBlock.contains("results");
    }
}
