package testng._18;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for TestNG Case 18 - JUnitXMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_allTests.
 * 
 * Supports both static analysis and dynamic testing.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._18";
    private final String variant;
    private Object reporterInstance;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_18/" + variant + "/JUnitXMLReporter.java");
        return Files.readString(path);
    }
    
    // ========== Dynamic Testing Methods ==========
    
    /**
     * Initialize JUnitXMLReporter instance for dynamic testing.
     */
    public void initializeReporter() throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".JUnitXMLReporter";
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor();
        this.reporterInstance = constructor.newInstance();
    }
    
    /**
     * Invoke generateReport method dynamically.
     */
    public void invokeGenerateReport(Object context) throws Exception {
        if (reporterInstance == null) {
            initializeReporter();
        }
        Method method = reporterInstance.getClass().getMethod("generateReport", Object.class);
        method.invoke(reporterInstance, context);
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
                        invokeGenerateReport(new Object());
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
     * Check if generateReport method exists
     */
    public boolean hasGenerateReportMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("generateReport") || source.contains("void generateReport");
    }
    
    /**
     * Check if m_allTests field exists
     */
    public boolean hasAllTestsField() throws IOException {
        String source = readSourceCode();
        return source.contains("m_allTests");
    }
    
    /**
     * Check if iteration over m_allTests exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for(ITestResult tr : m_allTests)") || 
               source.contains("for (ITestResult tr : m_allTests)") ||
               source.contains("for(ITestResult tr: m_allTests)") ||
               source.contains("for(Object tr : m_allTests)") ||
               source.contains("for (Object tr : m_allTests)");
    }
    
    /**
     * Check if synchronized block is present for m_allTests iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (m_allTests)") || 
               source.contains("synchronized(m_allTests)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (m_allTests) { ... for ... m_allTests ... }
        int syncIndex = source.indexOf("synchronized (m_allTests)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(m_allTests)");
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
        
        // Check if iteration over m_allTests is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("m_allTests");
    }
}
