package testng._17;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import testng._17.mocks.MockTestContext;

/**
 * Driver for TestNG Case 17 - JUnitXMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_configIssues.
 * 
 * Supports both static analysis and dynamic testing.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._17";
    private final String variant;
    private Object reporterInstance;
    private Class<?> reporterClass;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    // ========== Mock Factory Methods ==========
    
    /**
     * Create a MockTestContext for dynamic testing.
     */
    public MockTestContext createMockTestContext(String name) {
        return new MockTestContext(name);
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_17/" + variant + "/JUnitXMLReporter.java");
        return Files.readString(path);
    }
    
    // ========== Dynamic Testing Methods ==========
    
    /**
     * Initialize JUnitXMLReporter instance for dynamic testing.
     */
    public void initializeReporter() throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".JUnitXMLReporter";
        this.reporterClass = Class.forName(className);
        Constructor<?> constructor = reporterClass.getConstructor();
        this.reporterInstance = constructor.newInstance();
    }
    
    // ========== Original Class Method Coverage ==========
    // All public methods from JUnitXMLReporter are exposed via reflection
    
    public void onTestStart(Object result) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onTestStart", 
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, result);
    }
    
    public void beforeConfiguration(Object tr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("beforeConfiguration",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, tr);
    }
    
    public void onTestSuccess(Object tr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onTestSuccess",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, tr);
    }
    
    public void onTestFailedButWithinSuccessPercentage(Object tr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onTestFailedButWithinSuccessPercentage",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, tr);
    }
    
    public void onTestFailure(Object tr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onTestFailure",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, tr);
    }
    
    public void onTestSkipped(Object tr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onTestSkipped",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, tr);
    }
    
    public void onStart(Object context) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onStart",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestContext"));
        method.invoke(reporterInstance, context);
    }
    
    public void onFinish(Object context) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onFinish",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestContext"));
        method.invoke(reporterInstance, context);
    }
    
    public void onConfigurationFailure(Object itr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onConfigurationFailure",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, itr);
    }
    
    public void onConfigurationSkip(Object itr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onConfigurationSkip",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, itr);
    }
    
    public void onConfigurationSuccess(Object itr) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getMethod("onConfigurationSuccess",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestResult"));
        method.invoke(reporterInstance, itr);
    }
    
    public void generateReport(Object context) throws Exception {
        if (reporterInstance == null) initializeReporter();
        Method method = reporterClass.getDeclaredMethod("generateReport",
            Class.forName(BASE_PACKAGE + ".requirements.org.testng.ITestContext"));
        method.setAccessible(true);  // protected method
        method.invoke(reporterInstance, context);
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
     * Check if m_configIssues field exists
     */
    public boolean hasConfigIssuesField() throws IOException {
        String source = readSourceCode();
        return source.contains("m_configIssues");
    }
    
    /**
     * Check if iteration over m_configIssues exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for(ITestResult tr : m_configIssues)") || 
               source.contains("for (ITestResult tr : m_configIssues)") ||
               source.contains("for(ITestResult tr: m_configIssues)") ||
               source.contains("for(Object tr : m_configIssues)") ||
               source.contains("for (Object tr : m_configIssues)");
    }
    
    /**
     * Check if synchronized block is present for m_configIssues iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (m_configIssues)") || 
               source.contains("synchronized(m_configIssues)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (m_configIssues) { ... for ... m_configIssues ... }
        int syncIndex = source.indexOf("synchronized (m_configIssues)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(m_configIssues)");
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
        
        // Check if iteration over m_configIssues is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("m_configIssues");
    }
}
