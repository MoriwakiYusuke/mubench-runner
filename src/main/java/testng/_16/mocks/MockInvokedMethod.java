package testng._16.mocks;

import testng._16.requirements.org.testng.IInvokedMethod;
import testng._16.requirements.org.testng.ITestNGMethod;
import testng._16.requirements.org.testng.ITestResult;

/**
 * Mock implementation of IInvokedMethod for testing.
 */
public class MockInvokedMethod implements IInvokedMethod {
    
    private final ITestNGMethod testMethod;
    private final ITestResult testResult;
    private final boolean isTestMethod;
    
    public MockInvokedMethod(ITestNGMethod method, ITestResult result, boolean isTestMethod) {
        this.testMethod = method;
        this.testResult = result;
        this.isTestMethod = isTestMethod;
    }
    
    @Override
    public ITestNGMethod getTestMethod() { return testMethod; }
    
    @Override
    public ITestResult getTestResult() { return testResult; }
    
    @Override
    public boolean isTestMethod() { return isTestMethod; }
    
    @Override
    public boolean isConfigurationMethod() { return !isTestMethod; }
    
    @Override
    public long getDate() { return testResult.getStartMillis(); }
}
