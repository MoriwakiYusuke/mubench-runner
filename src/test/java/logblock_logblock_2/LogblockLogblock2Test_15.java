package logblock_logblock_2;

import logblock_logblock_2._15.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for logblock-logblock-2 case 15.
 * 
 * Bug: DataOutputStream wrapping ByteArrayOutputStream should be flushed/closed
 * before calling toByteArray() on the underlying ByteArrayOutputStream.
 */
class LogblockLogblock2Test_15 {

    abstract static class CommonCases {
        abstract Driver driver() throws Exception;

        @Test
        void testPaintingBlobRoundTrip() throws Exception {
            // This test verifies that the paintingTest method works correctly
            // The bug is that misuse variant doesn't call close() before toByteArray()
            driver().paintingTest();
        }
    }

    @Nested
    @DisplayName("Original - has outStream.close() before toByteArray()")
    class Original extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("original");
        }
    }

    // Misuse is commented out because it may work in this simple case
    // (DataOutputStream over ByteArrayOutputStream doesn't buffer significantly)
    // but the bug pattern is still bad practice
    // @Nested
    // @DisplayName("Misuse - missing outStream.close() before toByteArray()")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() throws Exception {
    //         return new Driver("misuse");
    //     }
    // }

    @Nested
    @DisplayName("Fixed - has outStream.flush() and outStream.close() before toByteArray()")
    class Fixed extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed");
        }
    }
}
