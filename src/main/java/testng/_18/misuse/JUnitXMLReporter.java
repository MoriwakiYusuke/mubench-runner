package testng._18.misuse;

import java.util.List;
import java.util.Collections;

/**
 * Simplified JUnitXMLReporter for Case 18 - m_allTests synchronization bug.
 * 
 * MISUSE: Iterating over synchronized list m_allTests without synchronization.
 */
public class JUnitXMLReporter {
    
    // Synchronized list for all tests
    private List<Object> m_configIssues = Collections.synchronizedList(new java.util.ArrayList<>());
    private List<Object> m_allTests = Collections.synchronizedList(new java.util.ArrayList<>());
    
    public void generateReport(Object context) {
        // ... other code ...
        
        // No synchronized block around iteration
        for(Object tr : m_configIssues) {
            createElement(tr);
        }
        // MISUSE: No synchronized block around iteration of m_allTests
        for(Object tr : m_allTests) {
            createElement(tr);
        }
        
        // ... other code ...
    }
    
    private void createElement(Object tr) {
        // Implementation
    }
}
