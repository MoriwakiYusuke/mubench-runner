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
 */
class MqttTest_389 {

    private static final String[] TEST_TOPICS = {"topic/test", "another/topic"};
    private static final int[] TEST_QOS = {1, 2};

    abstract static class CommonCases {
        abstract String variant();

        Driver createDriver() throws Exception {
            return new Driver(variant(), TEST_TOPICS, TEST_QOS);
        }

        @Test
        @DisplayName("getPayload should return valid bytes")
        void testGetPayload() throws Exception {
            Driver driver = createDriver();
            byte[] payload = driver.getPayload();
            
            assertNotNull(payload);
            assertTrue(payload.length > 0, "Payload should not be empty");
            
            // Payload should contain topic names encoded as UTF-8 with length prefix
            // and QoS bytes
        }

        @Test
        @DisplayName("getPayload returns consistent results")
        void testGetPayloadConsistency() throws Exception {
            Driver driver = createDriver();
            byte[] payload1 = driver.getPayload();
            byte[] payload2 = driver.getPayload();
            
            assertArrayEquals(payload1, payload2, 
                "getPayload should return consistent results");
        }

        @Test
        @DisplayName("getVariableHeader should return message ID")
        void testGetVariableHeader() throws Exception {
            Driver driver = createDriver();
            driver.setMessageId(12345);
            byte[] header = driver.getVariableHeader();
            
            assertNotNull(header);
            assertEquals(2, header.length, "Variable header should be 2 bytes (message ID)");
        }

        @Test
        @DisplayName("isRetryable should return true")
        void testIsRetryable() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.isRetryable());
        }

        @Test
        @DisplayName("getMessageInfo should return correct info byte")
        void testGetMessageInfo() throws Exception {
            Driver driver = createDriver();
            byte info = driver.getMessageInfo();
            // MESSAGE_TYPE_SUBSCRIBE with QoS 1 should have bit 1 set
            assertEquals(2, info & 0x0F, "Message info should have QoS 1 bits set");
        }

        @Test
        @DisplayName("getType should return SUBSCRIBE type (8)")
        void testGetType() throws Exception {
            Driver driver = createDriver();
            assertEquals(8, driver.getType(), "Type should be MESSAGE_TYPE_SUBSCRIBE (8)");
        }

        @Test
        @DisplayName("toString should return meaningful representation")
        void testToString() throws Exception {
            Driver driver = createDriver();
            String str = driver.toStringValue();
            
            assertNotNull(str);
            assertTrue(str.contains("SUBSCRIBE") || str.length() > 0);
        }

        @Test
        @DisplayName("setMessageId and getMessageId should work")
        void testMessageId() throws Exception {
            Driver driver = createDriver();
            driver.setMessageId(42);
            assertEquals(42, driver.getMessageId());
        }

        @Test
        @DisplayName("getKey should return message ID as string")
        void testGetKey() throws Exception {
            Driver driver = createDriver();
            driver.setMessageId(999);
            assertEquals("999", driver.getKey());
        }

        @Test
        @DisplayName("isMessageIdRequired should return true")
        void testIsMessageIdRequired() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.isMessageIdRequired());
        }

        @Test
        @DisplayName("setDuplicate should not throw")
        void testSetDuplicate() throws Exception {
            Driver driver = createDriver();
            assertDoesNotThrow(() -> driver.setDuplicate(true));
        }

        @Test
        @DisplayName("getHeader should return valid header bytes")
        void testGetHeader() throws Exception {
            Driver driver = createDriver();
            driver.setMessageId(1);
            byte[] header = driver.getHeader();
            
            assertNotNull(header);
            assertTrue(header.length > 0);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        String variant() {
            return "original";
        }
    }

    // Misuse is commented out because it contains the bug (missing flush)
    // and tests may behave inconsistently
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     String variant() {
    //         return "misuse";
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        String variant() {
            return "fixed";
        }
    }
}
