package testng._22.mocks;

import testng._22.requirements.org.testng.*;
import testng._22.requirements.org.testng.xml.XmlTest;

import java.util.*;

/**
 * Mock implementation of ITestContext for testing XMLReporter.
 */
public class MockTestContext implements ITestContext {
    
    private final String name;
    private ISuite suite;
    private final Date startDate;
    private final Date endDate;
    private final String outputDirectory;
    private final Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<>());
    
    private IResultMap passedTests = new MockResultMap();
    private IResultMap failedTests = new MockResultMap();
    private IResultMap skippedTests = new MockResultMap();
    private IResultMap failedButWithinSuccessPercentageTests = new MockResultMap();
    private IResultMap passedConfigurations = new MockResultMap();
    private IResultMap failedConfigurations = new MockResultMap();
    private IResultMap skippedConfigurations = new MockResultMap();
    
    public MockTestContext(String name) {
        this.name = name;
        this.startDate = new Date();
        this.endDate = new Date();
        this.outputDirectory = "/tmp";
    }
    
    public void setSuite(ISuite suite) {
        this.suite = suite;
    }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public Date getStartDate() { return startDate; }
    
    @Override
    public Date getEndDate() { return endDate; }
    
    @Override
    public IResultMap getPassedTests() { return passedTests; }
    
    @Override
    public IResultMap getSkippedTests() { return skippedTests; }
    
    @Override
    public IResultMap getFailedButWithinSuccessPercentageTests() { return failedButWithinSuccessPercentageTests; }
    
    @Override
    public IResultMap getFailedTests() { return failedTests; }
    
    @Override
    public String[] getIncludedGroups() { return new String[0]; }
    
    @Override
    public String[] getExcludedGroups() { return new String[0]; }
    
    @Override
    public String getOutputDirectory() { return outputDirectory; }
    
    @Override
    public ISuite getSuite() { return suite; }
    
    @Override
    public ITestNGMethod[] getAllTestMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public String getHost() { return null; }
    
    @Override
    public Collection<ITestNGMethod> getExcludedMethods() { return new ArrayList<>(); }
    
    @Override
    public IResultMap getPassedConfigurations() { return passedConfigurations; }
    
    @Override
    public IResultMap getSkippedConfigurations() { return skippedConfigurations; }
    
    @Override
    public IResultMap getFailedConfigurations() { return failedConfigurations; }
    
    @Override
    public XmlTest getCurrentXmlTest() { return null; }
    
    @Override
    public Object getAttribute(String name) { return attributes.get(name); }
    
    @Override
    public void setAttribute(String name, Object value) { attributes.put(name, value); }
    
    @Override
    public Set<String> getAttributeNames() { return attributes.keySet(); }
    
    @Override
    public Object removeAttribute(String name) { return attributes.remove(name); }
    
    // Setters for test setup
    public void setPassedTests(IResultMap passedTests) { this.passedTests = passedTests; }
    public void setFailedTests(IResultMap failedTests) { this.failedTests = failedTests; }
    public void setSkippedTests(IResultMap skippedTests) { this.skippedTests = skippedTests; }
}
