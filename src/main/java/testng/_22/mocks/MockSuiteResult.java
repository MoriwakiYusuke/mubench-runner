package testng._22.mocks;

import testng._22.requirements.org.testng.*;

/**
 * Mock implementation of ISuiteResult for testing XMLReporter.
 */
public class MockSuiteResult implements ISuiteResult {
    
    private final ITestContext testContext;
    
    public MockSuiteResult(ITestContext testContext) {
        this.testContext = testContext;
    }
    
    @Override
    public ITestContext getTestContext() {
        return testContext;
    }
}
