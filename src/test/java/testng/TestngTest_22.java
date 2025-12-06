package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._22.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 22 - XMLReporter results synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over results map.
 * 
 * Tests include both static analysis and dynamic testing.
 */
class TestngTest_22 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        // ========== Static Analysis Tests ==========
        
        @Test
        void testGetSuiteAttributesMethodExists() throws Exception {
            assertTrue(driver().hasGetSuiteAttributesMethod(), 
                "getSuiteAttributes method should exist");
        }
        
        @Test
        void testResultsVariableExists() throws Exception {
            assertTrue(driver().hasResultsVariable(), 
                "results variable should exist");
        }
        
        @Test
        void testIterationExists() throws Exception {
            assertTrue(driver().hasIteration(), 
                "Iteration over results should exist");
        }
        
        @Test
        void testSynchronizedBlockPresent() throws Exception {
            assertTrue(driver().hasSynchronizedBlock(), 
                "synchronized(results) block should be present");
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
        void testGetSuiteAttributesInvocation() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            Object result = d.invokeGetSuiteAttributes(new Object());
            assertNotNull(result, "getSuiteAttributes should return non-null");
        }
        
        @Test
        void testConcurrentAccess() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            boolean success = d.testConcurrentAccess(4);
            assertTrue(success, "Concurrent access should not cause ConcurrentModificationException");
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
    //     
    //     // Override expectations for misuse version
    //     @Override
    //     @Test
    //     void testSynchronizedBlockPresent() throws Exception {
    //         assertFalse(driver().hasSynchronizedBlock(), 
    //             "Misuse should NOT have synchronized(results) block");
    //     }
    //     
    //     @Override
    //     @Test
    //     void testCorrectlyFixed() throws Exception {
    //         assertFalse(driver().isCorrectlyFixed(), 
    //             "Misuse should NOT be correctly fixed");
    //     }
    // }
}
