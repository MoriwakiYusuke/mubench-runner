package testng._22.mocks;

import testng._22.requirements.org.testng.*;

import java.util.*;

/**
 * Mock implementation of IResultMap for testing XMLReporter.
 */
public class MockResultMap implements IResultMap {
    
    private final Set<ITestResult> results = Collections.synchronizedSet(new LinkedHashSet<>());
    
    @Override
    public void addResult(ITestResult result) {
        results.add(result);
    }
    
    @Override
    public Set<ITestResult> getResults(ITestNGMethod method) {
        Set<ITestResult> matched = new LinkedHashSet<>();
        for (ITestResult result : results) {
            if (result.getMethod() != null && result.getMethod().equals(method)) {
                matched.add(result);
            }
        }
        return matched;
    }
    
    @Override
    public Set<ITestResult> getAllResults() {
        return new LinkedHashSet<>(results);
    }
    
    @Override
    public void removeResult(ITestNGMethod m) {
        results.removeIf(r -> r.getMethod() != null && r.getMethod().equals(m));
    }
    
    @Override
    public void removeResult(ITestResult r) {
        results.remove(r);
    }
    
    @Override
    public Collection<ITestNGMethod> getAllMethods() {
        Set<ITestNGMethod> methods = new LinkedHashSet<>();
        for (ITestResult result : results) {
            if (result.getMethod() != null) {
                methods.add(result.getMethod());
            }
        }
        return methods;
    }
    
    @Override
    public int size() {
        return results.size();
    }
}
