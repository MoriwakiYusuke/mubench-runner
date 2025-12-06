package testng._22.original;

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Properties;

/**
 * Simplified XMLReporter for Case 22 - results synchronization bug.
 * 
 * ORIGINAL (FIXED): Iterating over synchronized map results WITH synchronization.
 */
public class XMLReporter {
    
    private Properties getSuiteAttributes(Object suite) {
        Properties props = new Properties();
        props.setProperty("name", "TestSuite");

        // Calculate the duration
        Map<String, Object> results = getResults(suite);
        Date minStartDate = new Date();
        Date maxEndDate = null;
        
        // FIXED: synchronized block around iteration of results
        synchronized (results) {
            for (Map.Entry<String, Object> result : results.entrySet()) {
                // Process result
                Date startDate = new Date();
                Date endDate = new Date();
                if (minStartDate.after(startDate)) {
                    minStartDate = startDate;
                }
                if (maxEndDate == null || maxEndDate.before(endDate)) {
                    maxEndDate = endDate != null ? endDate : startDate;
                }
            }
        }

        if (maxEndDate == null) {
            maxEndDate = minStartDate;
        }
        return props;
    }
    
    private Map<String, Object> getResults(Object suite) {
        return Collections.synchronizedMap(new java.util.HashMap<>());
    }
}
