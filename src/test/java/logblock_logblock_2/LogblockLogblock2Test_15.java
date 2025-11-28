package logblock_logblock_2;

import logblock_logblock_2._15.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for logblock-logblock-2 case 15.
 * 
 * Bug: DataOutputStream wrapping ByteArrayOutputStream should be flushed/closed
 * before calling toByteArray() on the underlying ByteArrayOutputStream.
 * 
 * This test uses source code analysis to verify the bug pattern.
 */
class LogblockLogblock2Test_15 {

    abstract static class CommonCases {
        abstract Driver driver() throws Exception;

        @Test
        @DisplayName("Source file should exist and be readable")
        void testSourceFileExists() throws Exception {
            Driver d = driver();
            String sourceCode = d.readSourceCode();
            assertNotNull(sourceCode);
            assertFalse(sourceCode.isEmpty(), "Source code should not be empty");
        }

        @Test
        @DisplayName("Should close or flush DataOutputStream before toByteArray()")
        void testProperStreamClose() throws Exception {
            Driver d = driver();
            assertTrue(d.hasProperStreamClose(),
                "DataOutputStream should be closed or flushed before calling toByteArray()");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("original");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() throws Exception {
    //         return new Driver("misuse");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed");
        }
    }
}
