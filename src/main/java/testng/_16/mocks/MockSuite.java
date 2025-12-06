package testng._16.mocks;

import testng._16.requirements.org.testng.*;
import testng._16.requirements.org.testng.internal.annotations.IAnnotationFinder;
import testng._16.requirements.org.testng.xml.XmlSuite;
import com.google.inject.Injector;

import java.util.*;

/**
 * Mock implementation of ISuite for testing ChronologicalPanel.
 */
public class MockSuite implements ISuite {
    
    private final List<IInvokedMethod> invokedMethods;
    private final String name;
    
    public MockSuite(String name, List<IInvokedMethod> methods) {
        this.name = name;
        this.invokedMethods = Collections.synchronizedList(new ArrayList<>(methods));
    }
    
    @Override
    public List<IInvokedMethod> getAllInvokedMethods() { return invokedMethods; }
    
    @Override
    public String getName() { return name; }
    
    @Override
    public Map<String, ISuiteResult> getResults() { return Collections.synchronizedMap(new HashMap<>()); }
    
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
    public Object getAttribute(String name) { return null; }
    
    @Override
    public void setAttribute(String name, Object value) {}
    
    @Override
    public Set<String> getAttributeNames() { return new HashSet<>(); }
    
    @Override
    public Object removeAttribute(String name) { return null; }
}
