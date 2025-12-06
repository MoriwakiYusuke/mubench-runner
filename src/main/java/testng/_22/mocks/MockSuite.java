package testng._22.mocks;

import testng._22.requirements.org.testng.*;
import testng._22.requirements.org.testng.internal.annotations.IAnnotationFinder;
import testng._22.requirements.org.testng.xml.XmlSuite;
import com.google.inject.Injector;

import java.util.*;

/**
 * Mock implementation of ISuite for testing XMLReporter.
 * This mock is essential for testing the synchronization bug in getSuiteAttributes.
 */
public class MockSuite implements ISuite {
    
    private final String name;
    private final Map<String, Object> attributes = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, ISuiteResult> results = Collections.synchronizedMap(new HashMap<>());
    
    public MockSuite(String name) {
        this.name = name;
    }
    
    /**
     * Add a result to the suite - useful for testing iteration over results.
     */
    public void addResult(String name, ISuiteResult result) {
        results.put(name, result);
    }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public Map<String, ISuiteResult> getResults() { return results; }
    
    @Override
    public ITestObjectFactory getObjectFactory() { return null; }
    
    @Override
    public String getOutputDirectory() { return "/tmp"; }
    
    @Override
    public String getParallel() { return "none"; }
    
    @Override
    public String getParentModule() { return null; }
    
    @Override
    public String getGuiceStage() { return null; }
    
    @Override
    public String getParameter(String parameterName) { return null; }
    
    @Override
    public Map<String, Collection<ITestNGMethod>> getMethodsByGroups() { return new HashMap<>(); }
    
    @Override
    public List<IInvokedMethod> getAllInvokedMethods() { return new ArrayList<>(); }
    
    @Override
    public Collection<ITestNGMethod> getExcludedMethods() { return new ArrayList<>(); }
    
    @Override
    public void run() {}
    
    @Override
    public String getHost() { return null; }
    
    @Override
    public SuiteRunState getSuiteState() { return null; }
    
    @Override
    public IAnnotationFinder getAnnotationFinder() { return null; }
    
    @Override
    public XmlSuite getXmlSuite() { return null; }
    
    @Override
    public void addListener(ITestNGListener listener) {}
    
    @Override
    public Injector getParentInjector() { return null; }
    
    @Override
    public void setParentInjector(Injector injector) {}
    
    @Override
    public List<ITestNGMethod> getAllMethods() { return new ArrayList<>(); }
    
    @Override
    public Object getAttribute(String name) { return attributes.get(name); }
    
    @Override
    public void setAttribute(String name, Object value) { attributes.put(name, value); }
    
    @Override
    public Set<String> getAttributeNames() { return attributes.keySet(); }
    
    @Override
    public Object removeAttribute(String name) { return attributes.remove(name); }
}
