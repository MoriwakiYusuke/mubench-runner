package testng._16;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import testng._16.mocks.*;
import testng._16.requirements.org.testng.*;
import testng._16.requirements.org.testng.reporters.XMLStringBuffer;
import testng._16.requirements.org.testng.reporters.jq.Model;

/**
 * Driver for TestNG Case 16 - ChronologicalPanel synchronization bug.
 * 
 * This driver supports both:
 * - Static analysis: verify the presence of synchronized block in source code
 * - Dynamic testing: instantiate ChronologicalPanel and invoke methods
 * 
 * Bug: Missing synchronized block when iterating over a synchronized collection.
 * Fix: Add synchronized(invokedMethods) { ... } around the sort and iteration.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._16";
    private final String sourceFilePath;
    private final String variant;
    private Object panelInstance;
    private Model model;
    
    public Driver(String variant) {
        this.variant = variant;
        this.sourceFilePath = "src/main/java/" + BASE_PACKAGE.replace('.', '/') 
            + "/" + variant + "/ChronologicalPanel.java";
    }
    
    // ========== Dynamic Testing Methods ==========
    
    /**
     * Initialize the panel instance for dynamic testing.
     */
    public void initializePanel() throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".ChronologicalPanel";
        Class<?> clazz = Class.forName(className);
        
        // Create Model instance with empty suite list (required by ChronologicalPanel constructor)
        this.model = new Model(new ArrayList<>());
        
        Constructor<?> constructor = clazz.getConstructor(Model.class);
        this.panelInstance = constructor.newInstance(model);
    }
    
    /**
     * Create a mock ISuite with test methods for dynamic testing.
     */
    public ISuite createMockSuite(int methodCount) {
        List<IInvokedMethod> methods = new ArrayList<>();
        MockClass testClass = new MockClass("TestClass");
        
        for (int i = 0; i < methodCount; i++) {
            MockTestNGMethod testMethod = new MockTestNGMethod("testMethod" + i, testClass);
            MockTestResult testResult = new MockTestResult(
                "testMethod" + i, 
                System.currentTimeMillis() + (i * 100), // Stagger start times
                ITestResult.SUCCESS, 
                testMethod, 
                testClass
            );
            methods.add(new MockInvokedMethod(testMethod, testResult, true));
        }
        
        return new MockSuite("TestSuite", methods);
    }
    
    /**
     * Invoke getContent method on the panel dynamically.
     */
    public String invokeGetContent(ISuite suite) throws Exception {
        if (panelInstance == null) {
            initializePanel();
        }
        
        Method getContentMethod = panelInstance.getClass().getMethod(
            "getContent", ISuite.class, XMLStringBuffer.class);
        
        XMLStringBuffer xsb = new XMLStringBuffer();
        return (String) getContentMethod.invoke(panelInstance, suite, xsb);
    }
    
    /**
     * Test concurrent access to verify synchronization behavior.
     * Returns true if no ConcurrentModificationException occurred.
     */
    public boolean testConcurrentAccess(ISuite suite, int threadCount) throws Exception {
        if (panelInstance == null) {
            initializePanel();
        }
        
        final boolean[] success = {true};
        Thread[] threads = new Thread[threadCount];
        
        for (int i = 0; i < threadCount; i++) {
            threads[i] = new Thread(() -> {
                try {
                    for (int j = 0; j < 10; j++) {
                        invokeGetContent(suite);
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
     * Read the source code of the ChronologicalPanel class.
     */
    public String readSourceCode() throws IOException {
        return Files.readString(Paths.get(sourceFilePath));
    }
    
    /**
     * Check if the source code contains the synchronized block pattern.
     * The fix requires: synchronized (invokedMethods) { ... }
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        // Check for synchronized(invokedMethods) pattern
        return source.contains("synchronized (invokedMethods)") 
            || source.contains("synchronized(invokedMethods)");
    }
    
    /**
     * Check if the getContent method exists in the source.
     */
    public boolean hasGetContentMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("public String getContent(");
    }
    
    /**
     * Check if Collections.sort is called on invokedMethods.
     */
    public boolean hasSortCall() throws IOException {
        String source = readSourceCode();
        return source.contains("Collections.sort(invokedMethods");
    }
    
    /**
     * Check if the code iterates over invokedMethods.
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for (IInvokedMethod im : invokedMethods)");
    }
    
    /**
     * Verify the correct fix pattern:
     * - synchronized block exists
     * - Sort and iteration are inside the synchronized block
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Find the synchronized block
        int syncStart = source.indexOf("synchronized (invokedMethods)");
        if (syncStart == -1) {
            syncStart = source.indexOf("synchronized(invokedMethods)");
        }
        if (syncStart == -1) {
            return false;
        }
        
        // Find the opening brace after synchronized
        int braceStart = source.indexOf("{", syncStart);
        if (braceStart == -1) {
            return false;
        }
        
        // Find the matching closing brace
        int braceCount = 1;
        int braceEnd = braceStart + 1;
        while (braceCount > 0 && braceEnd < source.length()) {
            char c = source.charAt(braceEnd);
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            braceEnd++;
        }
        
        // Extract the synchronized block content
        String syncBlock = source.substring(braceStart, braceEnd);
        
        // Check that both sort and iteration are inside the synchronized block
        boolean hasSortInside = syncBlock.contains("Collections.sort(invokedMethods");
        boolean hasIterationInside = syncBlock.contains("for (IInvokedMethod im : invokedMethods)");
        
        return hasSortInside && hasIterationInside;
    }
    
    public String getVariant() {
        return variant;
    }
    
    public String getSourceFilePath() {
        return sourceFilePath;
    }
}
