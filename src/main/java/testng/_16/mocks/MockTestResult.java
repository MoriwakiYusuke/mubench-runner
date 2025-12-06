package testng._16.mocks;

import testng._16.requirements.org.testng.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Mock implementation of ITestResult for testing.
 */
public class MockTestResult implements ITestResult {
    
    private final long startMillis;
    private long endMillis;
    private int status;
    private final ITestNGMethod method;
    private final IClass testClass;
    private String name;
    
    public MockTestResult(String name, long startMillis, int status, ITestNGMethod method, IClass testClass) {
        this.name = name;
        this.startMillis = startMillis;
        this.endMillis = startMillis + 100;
        this.status = status;
        this.method = method;
        this.testClass = testClass;
    }
    
    @Override
    public long getStartMillis() { return startMillis; }
    
    @Override
    public long getEndMillis() { return endMillis; }
    
    @Override
    public int getStatus() { return status; }
    
    @Override
    public void setStatus(int status) { this.status = status; }
    
    @Override
    public ITestNGMethod getMethod() { return method; }
    
    @Override
    public Object[] getParameters() { return new Object[0]; }
    
    @Override
    public void setParameters(Object[] parameters) {}
    
    @Override
    public IClass getTestClass() { return testClass; }
    
    @Override
    public Throwable getThrowable() { return null; }
    
    @Override
    public void setThrowable(Throwable throwable) {}
    
    @Override
    public String getName() { return name; }
    
    @Override
    public String getTestName() { return name; }
    
    @Override
    public void setTestName(String name) { this.name = name; }
    
    @Override
    public String getHost() { return "localhost"; }
    
    @Override
    public Object getInstance() { return null; }
    
    @Override
    public String getInstanceName() { return name; }
    
    @Override
    public ITestContext getTestContext() { return null; }
    
    @Override
    public int compareTo(ITestResult o) { return Long.compare(startMillis, o.getStartMillis()); }
    
    @Override
    public Object getAttribute(String name) { return null; }
    
    @Override
    public void setAttribute(String name, Object value) {}
    
    @Override
    public Set<String> getAttributeNames() { return Set.of(); }
    
    @Override
    public Object removeAttribute(String name) { return null; }
    
    @Override
    public void setEndMillis(long millis) { this.endMillis = millis; }

    @Override
    public boolean isSuccess() { return status == SUCCESS; }

    @Override
    public String id() { return name; }

    @Override
    public boolean wasRetried() { return false; }

    @Override
    public void setWasRetried(boolean wasRetried) {}

    @Override
    public List<ITestNGMethod> getSkipCausedBy() { return Collections.emptyList(); }

    @Override
    public Object[] getFactoryParameters() { return new Object[0]; }
}
