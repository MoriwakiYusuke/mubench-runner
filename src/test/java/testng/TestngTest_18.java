package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._18.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 18 - JUnitXMLReporter m_allTests synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_allTests.
 */
class TestngTest_18 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
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
