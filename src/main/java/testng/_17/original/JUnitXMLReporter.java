package testng._17.original;

import java.util.List;
import java.util.Collections;

/**
 * Simplified JUnitXMLReporter for Case 17 - m_configIssues synchronization bug.
 * 
 * ORIGINAL (FIXED): Iterating over synchronized list m_configIssues WITH synchronization.
 */
public class JUnitXMLReporter {
    
    // Synchronized list for config issues
    private List<Object> m_configIssues = Collections.synchronizedList(new java.util.ArrayList<>());
    private List<Object> m_allTests = Collections.synchronizedList(new java.util.ArrayList<>());
    
    public void generateReport(Object context) {
        // ... other code ...
        
        // FIXED: synchronized block around iteration of m_configIssues
        synchronized (m_configIssues) {
            for(Object tr : m_configIssues) {
                createElement(tr);
            }
        }
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
