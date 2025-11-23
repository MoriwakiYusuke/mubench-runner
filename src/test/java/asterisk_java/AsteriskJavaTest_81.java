package asterisk_java._81;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * Exercises AsyncAgiEvent decoding behaviour via the Driver helper.
 */
public class AsteriskJavaTest_81 {

    private static final String ENCODED_ENV =
        "agi_channel%3A%20%E2%9C%93\n" +
        "agi_language%3A%20ja";

    private static final String ENCODED_RESULT = "200%20result%3d0";

    abstract static class CommonCase {
        abstract Driver driver();

        @Test
        @DisplayName("decodeEnv decodes UTF-8 payloads")
        void decodeEnv() throws Exception {
            List<String> lines = driver().decodeEnv(ENCODED_ENV);
            assertFalse(lines.isEmpty(), "decoded env should not be empty");
            assertEquals("agi_channel: ✓", lines.get(0));
        }

        @Test
        @DisplayName("decodeResult keeps tokens intact")
        void decodeResult() throws Exception {
            List<String> parts = driver().decodeResult(ENCODED_RESULT);
            assertFalse(parts.isEmpty(), "decoded result should not be empty");
            assertEquals("200 result=0", parts.get(0));
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("asterisk_java._81.original.AsyncAgiEvent");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCase {
//        @Override
//        Driver driver() {
//            return new Driver("asterisk_java._81.misuse.AsyncAgiEvent");
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("asterisk_java._81.fixed.AsyncAgiEvent");
        }
    }
}
