package jriecken_gae_java_mini_profiler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import jriecken_gae_java_mini_profiler._39.Driver;

/**
 * Test class for jriecken-gae-java-mini-profiler case 39.
 * 
 * Bug: MiniProfilerAppstats.getAppstatsDataFor() calls Long.parseLong() 
 * without handling NumberFormatException.
 * 
 * - Original: Has try-catch for NumberFormatException (correct)
 * - Misuse: No exception handling (bug)
 * - Fixed: Has try-catch for NumberFormatException (correct)
 */
public class Jriecken_gae_java_mini_profilerTest_39 {
    
    private static final String BASE_PACKAGE = "jriecken_gae_java_mini_profiler._39";
    
    /**
     * Common test logic for all variants.
     */
    abstract static class CommonCases {
        
        abstract Driver driver();
        
        /**
         * Test that invalid (non-numeric) appstatsId is handled gracefully
         * without throwing NumberFormatException.
         */
        @Test
        @DisplayName("Should handle invalid appstatsId gracefully")
        void testHandlesInvalidIdGracefully() {
            Driver d = driver();
            
            // Test with various invalid inputs
            assertTrue(d.handlesInvalidIdGracefully("invalid"), 
                "Should handle 'invalid' string without throwing NumberFormatException");
            assertTrue(d.handlesInvalidIdGracefully("abc123"), 
                "Should handle 'abc123' string without throwing NumberFormatException");
            assertTrue(d.handlesInvalidIdGracefully(""), 
                "Should handle empty string without throwing NumberFormatException");
            assertTrue(d.handlesInvalidIdGracefully("12.34"), 
                "Should handle decimal string without throwing NumberFormatException");
        }
        
        /**
         * Test that valid numeric appstatsId is processed correctly.
         */
        @Test
        @DisplayName("Should process valid appstatsId correctly")
        void testHandlesValidIdCorrectly() {
            Driver d = driver();
            
            // Test with valid numeric ID
            assertTrue(d.handlesValidIdCorrectly("12345"), 
                "Should process valid numeric ID correctly");
            assertTrue(d.handlesValidIdCorrectly("0"), 
                "Should process '0' correctly");
            assertTrue(d.handlesValidIdCorrectly("-1"), 
                "Should process negative number correctly");
        }
    }
    
    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.MiniProfilerAppstats");
        }
    }
    
    // Misuse: バグがあるためテストは失敗する（コメントアウト）
    // NumberFormatException を catch していないため、invalid ID で例外がスローされる
    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonCases {
        
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".misuse.MiniProfilerAppstats");
        }
    }
    */
    
    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.MiniProfilerAppstats");
        }
    }
}
