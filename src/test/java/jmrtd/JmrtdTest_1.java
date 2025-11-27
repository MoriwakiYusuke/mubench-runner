package jmrtd;

import jmrtd._1.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for jmrtd Case 1: DataOutputStream not closed in SecureMessagingWrapper
 * 
 * Bug: DataOutputStream.close() not called in readDO8E() method
 * - original: has dataOut.close() in try-finally (correct)
 * - misuse: missing dataOut.close() (BUG - resource leak)
 * - fixed: LLM correctly added try-finally with dataOut.close()
 * 
 * This test uses source code analysis via Driver to verify the fix.
 */
class JmrtdTest_1 {

    private static final String BASE_PACKAGE = "jmrtd._1";

    /**
     * Common test cases that are executed against each variant.
     * Each nested class provides its own Driver instance pointing to the variant.
     */
    abstract static class CommonCases {

        abstract Driver driver();

        @Test
        @DisplayName("Source file should exist and be readable")
        void testSourceFileExists() throws IOException {
            Driver d = driver();
            String sourceCode = d.readSourceCode();
            assertNotNull(sourceCode);
            assertFalse(sourceCode.isEmpty(), "Source code should not be empty");
        }

        @Test
        @DisplayName("readDO8E method should properly close DataOutputStream")
        void testDataOutputStreamClose() throws IOException {
            Driver d = driver();
            assertTrue(d.hasDataOutputStreamClose(),
                "readDO8E() should call dataOut.close() for proper resource management");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.SecureMessagingWrapper");
        }
    }

    /**
     * Misuse variant - always fails because it lacks dataOut.close()
     * Commented out as it represents the buggy code that should fail.
     */
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //
    //     @Override
    //     Driver driver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.SecureMessagingWrapper");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {

        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.SecureMessagingWrapper");
        }
    }
}
