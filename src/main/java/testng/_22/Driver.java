package testng._22;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Properties;

import testng._22.requirements.org.testng.ISuite;
import testng._22.requirements.org.testng.xml.XmlSuite;
import testng._22.mocks.MockSuite;
import testng._22.mocks.MockTestContext;
import testng._22.mocks.MockSuiteResult;

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
    private Class<?> reporterClass;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    // ========== Mock Factory Methods ==========
    
    /**
     * Create a MockSuite for dynamic testing.
     */
    public MockSuite createMockSuite(String name) {
        return new MockSuite(name);
    }
    
    /**
     * Create a MockTestContext for dynamic testing.
     */
    public MockTestContext createMockTestContext(String name) {
        return new MockTestContext(name);
    }
    
    /**
     * Create a MockSuiteResult for dynamic testing.
     */
    public MockSuiteResult createMockSuiteResult(MockTestContext context) {
        return new MockSuiteResult(context);
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
        this.reporterClass = Class.forName(className);
        Constructor<?> constructor = reporterClass.getConstructor();
        this.reporterInstance = constructor.newInstance();
    }
    
    // ========== Original Class Method Coverage ==========
    // All public methods from XMLReporter are exposed via reflection
    
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("generateReport", List.class, List.class, String.class);
        method.invoke(reporterInstance, xmlSuites, suites, outputDirectory);
    }
    
    public static void addDurationAttributes(Object config, Properties attributes, 
            java.util.Date minStartDate, java.util.Date maxEndDate) throws Exception {
        Class<?> clazz = Class.forName("testng._22.original.XMLReporter");
        Class<?> configClass = Class.forName("testng._22.requirements.org.testng.reporters.XMLReporterConfig");
        Method method = clazz.getMethod("addDurationAttributes", configClass, Properties.class, java.util.Date.class, java.util.Date.class);
        method.invoke(null, config, attributes, minStartDate, maxEndDate);
    }
    
    public int getFileFragmentationLevel() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("getFileFragmentationLevel");
        return (int) method.invoke(reporterInstance);
    }
    
    public void setFileFragmentationLevel(int fileFragmentationLevel) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setFileFragmentationLevel", int.class);
        method.invoke(reporterInstance, fileFragmentationLevel);
    }
    
    public int getStackTraceOutputMethod() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("getStackTraceOutputMethod");
        return (int) method.invoke(reporterInstance);
    }
    
    public void setStackTraceOutputMethod(int stackTraceOutputMethod) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setStackTraceOutputMethod", int.class);
        method.invoke(reporterInstance, stackTraceOutputMethod);
    }
    
    public String getOutputDirectory() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("getOutputDirectory");
        return (String) method.invoke(reporterInstance);
    }
    
    public void setOutputDirectory(String outputDirectory) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setOutputDirectory", String.class);
        method.invoke(reporterInstance, outputDirectory);
    }
    
    public boolean isGenerateGroupsAttribute() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("isGenerateGroupsAttribute");
        return (boolean) method.invoke(reporterInstance);
    }
    
    public void setGenerateGroupsAttribute(boolean generateGroupsAttribute) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setGenerateGroupsAttribute", boolean.class);
        method.invoke(reporterInstance, generateGroupsAttribute);
    }
    
    public boolean isSplitClassAndPackageNames() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("isSplitClassAndPackageNames");
        return (boolean) method.invoke(reporterInstance);
    }
    
    public void setSplitClassAndPackageNames(boolean splitClassAndPackageNames) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setSplitClassAndPackageNames", boolean.class);
        method.invoke(reporterInstance, splitClassAndPackageNames);
    }
    
    public String getTimestampFormat() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("getTimestampFormat");
        return (String) method.invoke(reporterInstance);
    }
    
    public void setTimestampFormat(String timestampFormat) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setTimestampFormat", String.class);
        method.invoke(reporterInstance, timestampFormat);
    }
    
    public boolean isGenerateDependsOnMethods() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("isGenerateDependsOnMethods");
        return (boolean) method.invoke(reporterInstance);
    }
    
    public void setGenerateDependsOnMethods(boolean generateDependsOnMethods) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setGenerateDependsOnMethods", boolean.class);
        method.invoke(reporterInstance, generateDependsOnMethods);
    }
    
    public void setGenerateDependsOnGroups(boolean generateDependsOnGroups) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setGenerateDependsOnGroups", boolean.class);
        method.invoke(reporterInstance, generateDependsOnGroups);
    }
    
    public boolean isGenerateDependsOnGroups() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("isGenerateDependsOnGroups");
        return (boolean) method.invoke(reporterInstance);
    }
    
    public void setGenerateTestResultAttributes(boolean generateTestResultAttributes) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("setGenerateTestResultAttributes", boolean.class);
        method.invoke(reporterInstance, generateTestResultAttributes);
    }
    
    public boolean isGenerateTestResultAttributes() throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("isGenerateTestResultAttributes");
        return (boolean) method.invoke(reporterInstance);
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
        
        // Check if iteration is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("results.entrySet()") ||
               syncBlock.contains("for (Map.Entry") ||
               syncBlock.contains("for(Map.Entry");
    }
}
