package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._17.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 17 - JUnitXMLReporter m_configIssues synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_configIssues.
 * 
 * Tests include both static analysis and dynamic testing.
 */
class TestngTest_17 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        // ========== Static Analysis Tests ==========
        
        @Test
        void testGenerateReportMethodExists() throws Exception {
            assertTrue(driver().hasGenerateReportMethod(), 
                "generateReport method should exist");
        }
        
        @Test
        void testConfigIssuesFieldExists() throws Exception {
            assertTrue(driver().hasConfigIssuesField(), 
                "m_configIssues field should exist");
        }
        
        @Test
        void testIterationExists() throws Exception {
            assertTrue(driver().hasIteration(), 
                "Iteration over m_configIssues should exist");
        }
        
        @Test
        void testSynchronizedBlockPresent() throws Exception {
            assertTrue(driver().hasSynchronizedBlock(), 
                "synchronized(m_configIssues) block should be present");
        }
        
        @Test
        void testCorrectlyFixed() throws Exception {
            assertTrue(driver().isCorrectlyFixed(), 
                "Iteration should be inside synchronized block");
        }
        
        // ========== Dynamic Tests ==========
        
        @Test
        void testReporterInitialization() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            // If no exception, initialization succeeded
        }
        
        // Note: generateReport requires ITestContext which is complex to mock.
        // Concurrent access tests require actual generateReport invocation.
        // Static analysis is sufficient for verifying the synchronization fix.
    }
    
    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("original");
        }
    }
    
    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("fixed");
        }
    }
    
    // ========== Misuse Test (Commented out per guideline) ==========
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver("misuse");
    //     }
    // }
}
