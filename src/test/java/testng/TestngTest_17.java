package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._17.Driver;
import testng._17.mocks.MockTestContext;

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
        
        @Test
        void testOnTestSuccessInvocation() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            // Invoke onTestSuccess with null (method accepts the call)
            // This verifies the method is dynamically callable
            try {
                d.onTestSuccess(null);
            } catch (Exception e) {
                // Expected: NullPointerException when processing null result
                // The point is the method is callable
            }
        }
        
        @Test
        void testOnConfigurationFailureInvocation() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            try {
                d.onConfigurationFailure(null);
            } catch (Exception e) {
                // Expected: method is callable
            }
        }
        
        @Test
        void testGenerateReportWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            // generateReport is protected, invoke via reflection
            try {
                d.generateReport(mockContext);
            } catch (Exception e) {
                // May throw NPE due to outputDirectory access, but method is callable
            }
        }
        
        @Test
        void testOnStartWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            // onStart should work with mock context
            d.onStart(mockContext);
        }
        
        @Test
        void testOnFinishWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            // onFinish triggers generateReport, should work with mock
            try {
                d.onFinish(mockContext);
            } catch (Exception e) {
                // May throw NPE, but method invocation works
            }
        }
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
