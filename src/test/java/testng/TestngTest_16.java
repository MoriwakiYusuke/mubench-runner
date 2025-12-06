package testng;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._16.Driver;
import testng._16.requirements.org.testng.ISuite;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 16 - ChronologicalPanel synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over a synchronized collection.
 * 
 * Tests include both:
 * - Static analysis: verify synchronized block in source code
 * - Dynamic testing: instantiate panel and invoke methods
 */
class TestngTest_16 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        // ========== Static Analysis Tests ==========
        
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
        
        // ========== Dynamic Tests ==========
        
        @Test
        void testPanelInitialization() throws Exception {
            Driver d = driver();
            d.initializePanel();
            // If no exception, initialization succeeded
        }
        
        @Test
        void testGetContentWithMockSuite() throws Exception {
            Driver d = driver();
            d.initializePanel();
            ISuite mockSuite = d.createMockSuite(5);
            String content = d.invokeGetContent(mockSuite);
            assertNotNull(content, "getContent should return non-null");
        }
        
        @Test
        void testGetContentWithEmptySuite() throws Exception {
            Driver d = driver();
            d.initializePanel();
            ISuite mockSuite = d.createMockSuite(0);
            String content = d.invokeGetContent(mockSuite);
            assertNotNull(content, "getContent should handle empty suite");
        }
        
        @Test
        void testConcurrentAccess() throws Exception {
            Driver d = driver();
            d.initializePanel();
            ISuite mockSuite = d.createMockSuite(10);
            boolean success = d.testConcurrentAccess(mockSuite, 4);
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
        
        // Original は不完全な修正版のため、このテストは失敗が期待動作
        @Override
        @Test
        @Disabled("Original has incomplete fix - sort is outside synchronized block")
        void testCorrectlyFixed() throws Exception {
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
    
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver("misuse");
    //     }
    // }
}
