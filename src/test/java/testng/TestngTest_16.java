package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._16.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 16 - ChronologicalPanel synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over a synchronized collection.
 */
class TestngTest_16 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        @Test
        void testGetContentMethodExists() throws Exception {
            assertTrue(driver().hasGetContentMethod(), 
                "getContent method should exist");
        }
        
        @Test
        void testSortCallExists() throws Exception {
            assertTrue(driver().hasSortCall(), 
                "Collections.sort call should exist");
        }
        
        @Test
        void testIterationExists() throws Exception {
            assertTrue(driver().hasIteration(), 
                "Iteration over invokedMethods should exist");
        }
        
        @Test
        void testSynchronizedBlockPresent() throws Exception {
            assertTrue(driver().hasSynchronizedBlock(), 
                "synchronized(invokedMethods) block should be present");
        }
        
        @Test
        void testCorrectlyFixed() throws Exception {
            assertTrue(driver().isCorrectlyFixed(), 
                "Sort and iteration should be inside synchronized block");
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
