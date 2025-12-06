package testng._18.requirements.org.testng.internal;

import testng._18.requirements.org.testng.ITestResult;

/**
 * A convenient interface to use when implementing listeners.
 * Simplified version for mubench testing.
 */
public interface IResultListener2 {
    void onTestStart(ITestResult result);
    void beforeConfiguration(ITestResult tr);
    void onTestSuccess(ITestResult tr);
    void onTestFailedButWithinSuccessPercentage(ITestResult tr);
    void onTestFailure(ITestResult tr);
    void onTestSkipped(ITestResult tr);
    void onStart(testng._18.requirements.org.testng.ITestContext context);
    void onFinish(testng._18.requirements.org.testng.ITestContext context);
    void onConfigurationFailure(ITestResult itr);
    void onConfigurationSkip(ITestResult itr);
    void onConfigurationSuccess(ITestResult itr);
}
