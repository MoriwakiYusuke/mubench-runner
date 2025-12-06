package testng._16.mocks;

import testng._16.requirements.org.testng.*;
import testng._16.requirements.org.testng.internal.ConstructorOrMethod;
import testng._16.requirements.org.testng.xml.XmlTest;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Mock implementation of ITestNGMethod for testing.
 */
public class MockTestNGMethod implements ITestNGMethod {
    
    private final String methodName;
    private ITestClass testClass;
    
    public MockTestNGMethod(String methodName, ITestClass testClass) {
        this.methodName = methodName;
        this.testClass = testClass;
    }
    
    @Override
    public String getMethodName() { return methodName; }
    
    @Override
    public ITestClass getTestClass() { return testClass; }
    
    @Override
    public void setTestClass(ITestClass cls) { this.testClass = cls; }
    
    @Override
    public Class<?> getRealClass() { return testClass != null ? testClass.getRealClass() : Object.class; }
    
    @Override
    public Object getInstance() { return null; }
    
    @Override
    public long[] getInstanceHashCodes() { return new long[0]; }
    
    @Override
    public String[] getGroups() { return new String[0]; }
    
    @Override
    public String[] getGroupsDependedUpon() { return new String[0]; }
    
    @Override
    public String[] getMethodsDependedUpon() { return new String[0]; }
    
    @Override
    public void addMethodDependedUpon(String methodName) {}
    
    @Override
    public String getMissingGroup() { return null; }
    
    @Override
    public void setMissingGroup(String group) {}
    
    @Override
    public String[] getBeforeGroups() { return new String[0]; }
    
    @Override
    public String[] getAfterGroups() { return new String[0]; }
    
    @Override
    public boolean isTest() { return true; }
    
    @Override
    public boolean isBeforeSuiteConfiguration() { return false; }
    
    @Override
    public boolean isAfterSuiteConfiguration() { return false; }
    
    @Override
    public boolean isBeforeTestConfiguration() { return false; }
    
    @Override
    public boolean isAfterTestConfiguration() { return false; }
    
    @Override
    public boolean isBeforeClassConfiguration() { return false; }
    
    @Override
    public boolean isAfterClassConfiguration() { return false; }
    
    @Override
    public boolean isBeforeMethodConfiguration() { return false; }
    
    @Override
    public boolean isAfterMethodConfiguration() { return false; }
    
    @Override
    public boolean isBeforeGroupsConfiguration() { return false; }
    
    @Override
    public boolean isAfterGroupsConfiguration() { return false; }
    
    @Override
    public long getTimeOut() { return 0; }
    
    @Override
    public void setTimeOut(long timeOut) {}
    
    @Override
    public int getInvocationCount() { return 1; }
    
    @Override
    public void setInvocationCount(int count) {}
    
    @Override
    public int getSuccessPercentage() { return 100; }
    
    @Override
    public String getId() { return methodName; }
    
    @Override
    public void setId(String id) {}
    
    @Override
    public long getDate() { return System.currentTimeMillis(); }
    
    @Override
    public void setDate(long date) {}
    
    @Override
    public boolean canRunFromClass(IClass testClass) { return true; }
    
    @Override
    public boolean isAlwaysRun() { return false; }
    
    @Override
    public int getThreadPoolSize() { return 0; }
    
    @Override
    public void setThreadPoolSize(int threadPoolSize) {}
    
    @Override
    public boolean getEnabled() { return true; }
    
    @Override
    public String getDescription() { return ""; }
    
    @Override
    public void setDescription(String description) {}
    
    @Override
    public void incrementCurrentInvocationCount() {}
    
    @Override
    public int getCurrentInvocationCount() { return 0; }
    
    @Override
    public void setParameterInvocationCount(int n) {}
    
    @Override
    public int getParameterInvocationCount() { return 0; }
    
    @Override
    public void setMoreInvocationChecker(Callable<Boolean> moreInvocationChecker) {}
    
    @Override
    public boolean hasMoreInvocation() { return false; }
    
    @Override
    public ITestNGMethod clone() { return this; }
    
    @Override
    public IRetryAnalyzer getRetryAnalyzer(ITestResult result) { return null; }
    
    @Override
    public void setRetryAnalyzerClass(Class<? extends IRetryAnalyzer> clazz) {}
    
    @Override
    public Class<? extends IRetryAnalyzer> getRetryAnalyzerClass() { return null; }
    
    @Override
    public boolean skipFailedInvocations() { return false; }
    
    @Override
    public void setSkipFailedInvocations(boolean skip) {}
    
    @Override
    public long getInvocationTimeOut() { return 0; }
    
    @Override
    public boolean ignoreMissingDependencies() { return false; }
    
    @Override
    public void setIgnoreMissingDependencies(boolean ignore) {}
    
    @Override
    public List<Integer> getInvocationNumbers() { return List.of(); }
    
    @Override
    public void setInvocationNumbers(List<Integer> numbers) {}
    
    @Override
    public void addFailedInvocationNumber(int number) {}
    
    @Override
    public List<Integer> getFailedInvocationNumbers() { return List.of(); }
    
    @Override
    public int getPriority() { return 0; }
    
    @Override
    public void setPriority(int priority) {}
    
    @Override
    public int getInterceptedPriority() { return 0; }
    
    @Override
    public void setInterceptedPriority(int priority) {}
    
    @Override
    public XmlTest getXmlTest() { return null; }
    
    @Override
    public ConstructorOrMethod getConstructorOrMethod() { return null; }
    
    @Override
    public Map<String, String> findMethodParameters(XmlTest test) { return Map.of(); }
    
    @Override
    public String getQualifiedName() { return testClass.getName() + "." + methodName; }
}
