package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._21.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 21 - Model.java synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over suite.getResults().
 */
class TestngTest_21 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        @Test
        void testInitMethodExists() throws Exception {
            assertTrue(driver().hasInitMethod(), 
                "init() method should exist");
        }
        
        @Test
        void testGetResultsCallExists() throws Exception {
            assertTrue(driver().hasGetResultsCall(), 
                "getResults() call should exist");
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
    }
    
    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("original");
        }
    }
    
    // Misuse variant - tests FAIL as expected (synchronized block is missing)
    // Confirmed: testSynchronizedBlockPresent() and testCorrectlyFixed() fail
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver("misuse");
    //     }
    // }
    
    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("fixed");
        }
    }
}
