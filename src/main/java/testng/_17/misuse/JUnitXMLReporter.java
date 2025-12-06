package testng._17.misuse;

import java.util.List;
import java.util.Collections;

/**
 * Simplified JUnitXMLReporter for Case 17 - m_configIssues synchronization bug.
 * 
 * MISUSE: Iterating over synchronized list m_configIssues without synchronization.
 */
public class JUnitXMLReporter {
    
    // Synchronized list for config issues
    private List<Object> m_configIssues = Collections.synchronizedList(new java.util.ArrayList<>());
    private List<Object> m_allTests = Collections.synchronizedList(new java.util.ArrayList<>());
    
    public void generateReport(Object context) {
        // ... other code ...
        
        // MISUSE: No synchronized block around iteration
        for(Object tr : m_configIssues) {
            createElement(tr);
        }
        for(Object tr : m_allTests) {
            createElement(tr);
        }
        
        // ... other code ...
    }
    
    private void createElement(Object tr) {
        // Implementation
    }
}
