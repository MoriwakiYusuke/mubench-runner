package testng._21;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import testng._21.requirements.org.testng.*;

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
    private Class<?> modelClass;
    
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
        this.modelClass = Class.forName(className);
        Constructor<?> constructor = modelClass.getConstructor(List.class);
        this.modelInstance = constructor.newInstance(new ArrayList<ISuite>());
    }
    
    /**
     * Initialize Model with a list of mock suites.
     */
    public void initializeModelWithSuites(List<ISuite> suites) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".Model";
        this.modelClass = Class.forName(className);
        Constructor<?> constructor = modelClass.getConstructor(List.class);
        this.modelInstance = constructor.newInstance(suites);
    }
    
    // ========== Original Class Method Coverage ==========
    // All public methods from Model are exposed via reflection
    
    public List<ISuite> getSuites() throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getSuites");
        return (List<ISuite>) method.invoke(modelInstance);
    }
    
    public Object getFailedResultsByClass(Object suite) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getFailedResultsByClass", ISuite.class);
        return method.invoke(modelInstance, suite);
    }
    
    public Object getSkippedResultsByClass(Object suite) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getSkippedResultsByClass", ISuite.class);
        return method.invoke(modelInstance, suite);
    }
    
    public Object getPassedResultsByClass(Object suite) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getPassedResultsByClass", ISuite.class);
        return method.invoke(modelInstance, suite);
    }
    
    public String getTag(Object tr) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getTag", ITestResult.class);
        return (String) method.invoke(modelInstance, tr);
    }
    
    public List<ITestResult> getTestResults(Object suite) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getTestResults", ISuite.class);
        return (List<ITestResult>) method.invoke(modelInstance, suite);
    }
    
    public static String getTestResultName(Object tr) throws Exception {
        Class<?> modelClass = Class.forName("testng._21.original.Model");
        Method method = modelClass.getMethod("getTestResultName", ITestResult.class);
        return (String) method.invoke(null, tr);
    }
    
    public List<ITestResult> getAllFailedResults() throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getAllFailedResults");
        return (List<ITestResult>) method.invoke(modelInstance);
    }
    
    public static String getImage(String tagClass) throws Exception {
        Class<?> modelClass = Class.forName("testng._21.original.Model");
        Method method = modelClass.getMethod("getImage", String.class);
        return (String) method.invoke(null, tagClass);
    }
    
    public String getStatusForSuite(String suiteName) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getStatusForSuite", String.class);
        return (String) method.invoke(modelInstance, suiteName);
    }
    
    public <T> Set<T> nonnullSet(Set<T> l) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("nonnullSet", Set.class);
        return (Set<T>) method.invoke(modelInstance, l);
    }
    
    public <T> List<T> nonnullList(List<T> l) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("nonnullList", List.class);
        return (List<T>) method.invoke(modelInstance, l);
    }
    
    public List<String> getGroups(String name) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getGroups", String.class);
        return (List<String>) method.invoke(modelInstance, name);
    }
    
    public List<String> getMethodsInGroup(String groupName) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getMethodsInGroup", String.class);
        return (List<String>) method.invoke(modelInstance, groupName);
    }
    
    public List<ITestResult> getAllTestResults(Object suite) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getAllTestResults", ISuite.class);
        return (List<ITestResult>) method.invoke(modelInstance, suite);
    }
    
    public List<ITestResult> getAllTestResults(Object suite, boolean testsOnly) throws Exception {
        if (modelInstance == null) initializeModel();
        Method method = modelClass.getMethod("getAllTestResults", ISuite.class, boolean.class);
        return (List<ITestResult>) method.invoke(modelInstance, suite, testsOnly);
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
               source.contains("synchronized(results)") ||
               source.contains("synchronized (suiteResults)") ||
               source.contains("synchronized(suiteResults)");
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
            syncIndex = source.indexOf("synchronized (suiteResults)");
        }
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(suiteResults)");
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
               syncBlock.contains("results.values()") ||
               syncBlock.contains("suiteResults.values()");
    }
}
