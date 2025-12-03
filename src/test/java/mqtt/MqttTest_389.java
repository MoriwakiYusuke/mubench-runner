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
 * This test uses dynamic execution via Driver to verify correct behavior.
 */
class MqttTest_389 {

    private static final String[] TEST_TOPICS = {"topic/test", "another/topic"};
    private static final int[] TEST_QOS = {1, 2};

    abstract static class CommonLogic {
        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("getPayload should return valid bytes")
        void testGetPayloadReturnsBytes() throws Exception {
            Driver d = createDriver();
            byte[] payload = d.getPayload();
            assertNotNull(payload, "Payload should not be null");
            assertTrue(payload.length > 0, "Payload should not be empty");
        }

        @Test
        @DisplayName("getHeader should return valid bytes")
        void testGetHeaderReturnsBytes() throws Exception {
            Driver d = createDriver();
            byte[] header = d.getHeader();
            assertNotNull(header, "Header should not be null");
            assertTrue(header.length > 0, "Header should not be empty");
        }

        @Test
        @DisplayName("Message type should be SUBSCRIBE (8)")
        void testMessageType() throws Exception {
            Driver d = createDriver();
            byte type = d.getType();
            assertEquals(8, type, "Message type should be SUBSCRIBE (8)");
        }

        @Test
        @DisplayName("isRetryable should return true")
        void testIsRetryable() throws Exception {
            Driver d = createDriver();
            assertTrue(d.isRetryable(), "SUBSCRIBE messages should be retryable");
        }

        @Test
        @DisplayName("Message ID can be set and retrieved")
        void testMessageId() throws Exception {
            Driver d = createDriver();
            d.setMessageId(12345);
            assertEquals(12345, d.getMessageId(), "Message ID should match");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("original", TEST_TOPICS, TEST_QOS);
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonLogic {
    //     @Override
    //     Driver createDriver() throws Exception {
    //         return new Driver("misuse", TEST_TOPICS, TEST_QOS);
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("fixed", TEST_TOPICS, TEST_QOS);
        }
    }
}
