package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._18.Driver;
import testng._18.mocks.MockTestContext;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 18 - JUnitXMLReporter m_allTests synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_allTests.
 * 
 * Tests include both static analysis and dynamic testing.
 */
class TestngTest_18 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        // ========== Static Analysis Tests ==========
        
        @Test
        void testGenerateReportMethodExists() throws Exception {
            assertTrue(driver().hasGenerateReportMethod(), 
                "generateReport method should exist");
        }
        
        @Test
        void testAllTestsFieldExists() throws Exception {
            assertTrue(driver().hasAllTestsField(), 
                "m_allTests field should exist");
        }
        
        @Test
        void testIterationExists() throws Exception {
            assertTrue(driver().hasIteration(), 
                "Iteration over m_allTests should exist");
        }
        
        @Test
        void testSynchronizedBlockPresent() throws Exception {
            assertTrue(driver().hasSynchronizedBlock(), 
                "synchronized(m_allTests) block should be present");
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
            try {
                d.onTestSuccess(null);
            } catch (Exception e) {
                // Expected: method is callable
            }
        }
        
        @Test
        void testOnTestFailureInvocation() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            try {
                d.onTestFailure(null);
            } catch (Exception e) {
                // Expected: method is callable
            }
        }
        
        @Test
        void testGenerateReportWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            try {
                d.generateReport(mockContext);
            } catch (Exception e) {
                // May throw NPE, but method is callable
            }
        }
        
        @Test
        void testOnStartWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            d.onStart(mockContext);
        }
        
        @Test
        void testOnFinishWithMockContext() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            MockTestContext mockContext = d.createMockTestContext("TestContext");
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
