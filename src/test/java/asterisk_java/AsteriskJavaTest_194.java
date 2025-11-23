package asterisk_java._194;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises RtcpReceivedEvent payload parsing via the Driver helper.
 */
public class AsteriskJavaTest_194 {

    private static final String VALID_VALUE = "200(Sender Report)";
    private static final String INVALID_VALUE = "PT: invalid";

    abstract static class CommonCase {
        abstract Driver driver();

        @Test
        @DisplayName("parsePayloadType extracts numeric code")
        void parseValidValue() throws Exception {
            Long pt = driver().parsePayloadType(VALID_VALUE);
            assertEquals(200L, pt);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("asterisk_java._194.original.RtcpReceivedEvent");
        }

        @Test
        @DisplayName("setPt reports helpful NumberFormatException")
        void parseInvalidValue() {
            Throwable error = driver().captureException(INVALID_VALUE);
            assertNotNull(error, "error should be reported");
            assertTrue(error instanceof NumberFormatException);
            assertEquals("Input string [PT: invalid] is not a parsable long", error.getMessage());
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCase {
//        @Override
//        Driver driver() {
//            return new Driver("asterisk_java._194.misuse.RtcpReceivedEvent");
//        }
//
//        @Test
//        @DisplayName("setPt propagates raw NumberFormatException")
//        void parseInvalidValue() {
//            Throwable error = driver().captureException(INVALID_VALUE);
//            assertNotNull(error, "error should be reported");
//            assertTrue(error instanceof NumberFormatException);
//            assertEquals("For input string: \"PT: invalid\"", error.getMessage());
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("asterisk_java._194.fixed.RtcpReceivedEvent");
        }

        @Test
        @DisplayName("setPt reports IllegalArgumentException with context")
        void parseInvalidValue() {
            Throwable error = driver().captureException(INVALID_VALUE);
            assertNotNull(error, "error should be reported");
            assertTrue(error instanceof IllegalArgumentException);
            assertTrue(error.getMessage().contains("Invalid payload type value"));
            assertTrue(error.getMessage().contains("PT: invalid"));
        }
    }
}
