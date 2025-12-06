package testng._18.original;

import java.util.List;
import java.util.Collections;

/**
 * Simplified JUnitXMLReporter for Case 18 - m_allTests synchronization bug.
 * 
 * ORIGINAL (FIXED): Iterating over synchronized list m_allTests WITH synchronization.
 */
public class JUnitXMLReporter {
    
    // Synchronized list for all tests
    private List<Object> m_configIssues = Collections.synchronizedList(new java.util.ArrayList<>());
    private List<Object> m_allTests = Collections.synchronizedList(new java.util.ArrayList<>());
    
    public void generateReport(Object context) {
        // ... other code ...
        
        synchronized (m_configIssues) {
            for(Object tr : m_configIssues) {
                createElement(tr);
            }
        }
        // FIXED: synchronized block around iteration of m_allTests
        synchronized (m_allTests) {
            for(Object tr : m_allTests) {
                createElement(tr);
            }
        }
        
        // ... other code ...
    }
    
    private void createElement(Object tr) {
        // Implementation
    }
}
