package testng._21;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import testng._21.mocks.*;
import testng._21.requirements.org.testng.*;
import testng._21.requirements.org.testng.reporters.jq.Model;

/**
 * Driver for TestNG Case 21 - Model.java synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over suite.getResults().
 * 
 * Supports both static analysis and dynamic testing.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._21";
    private final String variant;
    private Object modelInstance;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_21/" + variant + "/Model.java");
        return Files.readString(path);
    }
    
    // ========== Dynamic Testing Methods ==========
    
    /**
     * Initialize Model instance for dynamic testing.
     */
    public void initializeModel() throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".Model";
        Class<?> clazz = Class.forName(className);
        
        // Model constructor takes List<ISuite>
        Constructor<?> constructor = clazz.getConstructor(List.class);
        this.modelInstance = constructor.newInstance(new ArrayList<ISuite>());
    }
    
    /**
     * Create a mock ISuite with test methods.
     */
    public ISuite createMockSuite(int methodCount) {
        List<IInvokedMethod> methods = new ArrayList<>();
        MockClass testClass = new MockClass("TestClass");
        
        for (int i = 0; i < methodCount; i++) {
            MockTestNGMethod testMethod = new MockTestNGMethod("testMethod" + i, testClass);
            MockTestResult testResult = new MockTestResult(
                "testMethod" + i,
                System.currentTimeMillis() + (i * 100),
                ITestResult.SUCCESS,
                testMethod,
                testClass
            );
            methods.add(new MockInvokedMethod(testMethod, testResult, true));
        }
        
        return new MockSuite("TestSuite", methods);
    }
    
    /**
     * Initialize Model with a list of mock suites.
     */
    public void initializeModelWithSuites(List<ISuite> suites) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".Model";
        Class<?> clazz = Class.forName(className);
        Constructor<?> constructor = clazz.getConstructor(List.class);
        this.modelInstance = constructor.newInstance(suites);
    }
    
    /**
     * Get the model's suites list.
     */
    public List<ISuite> getSuites() throws Exception {
        if (modelInstance == null) {
            initializeModel();
        }
        Method getSuitesMethod = modelInstance.getClass().getMethod("getSuites");
        return (List<ISuite>) getSuitesMethod.invoke(modelInstance);
    }
    
    // ========== Static Analysis Methods ==========
    
    /**
     * Check if init() method exists
     */
    public boolean hasInitMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("private void init()") || source.contains("void init()");
    }
    
    /**
     * Check if getResults() call exists
     */
    public boolean hasGetResultsCall() throws IOException {
        String source = readSourceCode();
        return source.contains("suite.getResults()") || source.contains("getResults()");
    }
    
    /**
     * Check if iteration over results exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for (ISuiteResult sr :") || 
               source.contains("for(ISuiteResult sr :") ||
               source.contains("results.values()");
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
        
        // Check for pattern: synchronized (results) { ... for ... results.values() ... }
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
        
        // Check if iteration is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("for (ISuiteResult sr :") || 
               syncBlock.contains("for(ISuiteResult sr :") ||
               syncBlock.contains("results.values()");
    }
}
