package asterisk_java._194;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises RtcpReceivedEvent payload parsing via source code inspection.
 * The misuse is missing exception handling for NumberFormatException in setPt().
 */
public class AsteriskJavaTest_194 {

    private static final String VALID_VALUE = "200(Sender Report)";

    abstract static class CommonCase {
        abstract String sourceFile();

        @Test
        @DisplayName("parsePayloadType extracts numeric code")
        void parseValidValue() throws Exception {
            Driver driver = new Driver(sourceFile().replace("/", ".").replace(".java", ""));
            Long pt = driver.parsePayloadType(VALID_VALUE);
            assertEquals(200L, pt);
        }

        @Test
        @DisplayName("setPt handles NumberFormatException properly")
        void handlesNumberFormatException() throws Exception {
            String content = Files.readString(Path.of("src/main/java", sourceFile()));
            // Find the setPt method and check if it catches NumberFormatException
            int setPtIdx = content.indexOf("void setPt(");
            assertTrue(setPtIdx >= 0, "setPt method should exist");
            
            // Get the method body (find the next method or end of class)
            int methodEnd = content.indexOf("\n    public ", setPtIdx + 1);
            if (methodEnd < 0) methodEnd = content.length();
            String setPtMethod = content.substring(setPtIdx, methodEnd);
            
            // The fix is to catch NumberFormatException and provide a better message
            assertTrue(setPtMethod.contains("catch") && setPtMethod.contains("NumberFormatException"),
                "setPt should catch NumberFormatException to provide helpful error messages");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCase {
        @Override
        String sourceFile() {
            return "asterisk_java/_194/original/RtcpReceivedEvent.java";
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCase {
//        @Override
//        String sourceFile() {
//            return "asterisk_java/_194/misuse/RtcpReceivedEvent.java";
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCase {
        @Override
        String sourceFile() {
            return "asterisk_java/_194/fixed/RtcpReceivedEvent.java";
        }
    }
}
