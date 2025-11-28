package mqtt;

import mqtt._389.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for MqttSubscribe variants (mqtt/389).
 * 
 * Bug: DataOutputStream wrapping ByteArrayOutputStream should be flushed
 * before calling toByteArray() on the underlying stream.
 * 
 * This test uses source code analysis to verify the bug pattern.
 */
class MqttTest_389 {

    private static final String[] TEST_TOPICS = {"topic/test", "another/topic"};
    private static final int[] TEST_QOS = {1, 2};

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
        @DisplayName("getPayload() should flush DataOutputStream before toByteArray()")
        void testProperFlushInGetPayload() throws Exception {
            Driver d = driver();
            assertTrue(d.hasProperFlushInGetPayload(),
                "getPayload() should call dos.flush() before baos.toByteArray()");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("original", TEST_TOPICS, TEST_QOS);
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() throws Exception {
    //         return new Driver("misuse", TEST_TOPICS, TEST_QOS);
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed", TEST_TOPICS, TEST_QOS);
        }
    }
}
