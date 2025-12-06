package testng._16.mocks;

import testng._16.requirements.org.testng.ITestClass;
import testng._16.requirements.org.testng.ITestNGMethod;
import testng._16.requirements.org.testng.xml.XmlClass;
import testng._16.requirements.org.testng.xml.XmlTest;

/**
 * Mock implementation of ITestClass for testing.
 */
public class MockClass implements ITestClass {
    
    private final String name;
    private final Class<?> realClass;
    
    public MockClass(String name) {
        this.name = name;
        this.realClass = Object.class;
    }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public Class<?> getRealClass() { return realClass; }
    
    @Override
    public XmlTest getXmlTest() { return null; }
    
    @Override
    public XmlClass getXmlClass() { return null; }
    
    @Override
    public String getTestName() { return name; }
    
    @Override
    public Object[] getInstances(boolean create) { return new Object[0]; }
    
    @Override
    public ITestNGMethod[] getTestMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getBeforeClassMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getAfterClassMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getBeforeTestMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getAfterTestMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getBeforeSuiteMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getAfterSuiteMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getBeforeTestConfigurationMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getAfterTestConfigurationMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getBeforeGroupsMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public ITestNGMethod[] getAfterGroupsMethods() { return new ITestNGMethod[0]; }
    
    @Override
    public long[] getInstanceHashCodes() { return new long[0]; }
    
    @Override
    public void addInstance(Object instance) {}
}
