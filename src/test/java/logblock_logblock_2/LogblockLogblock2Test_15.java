package logblock_logblock_2;

import logblock_logblock_2._15.Driver;
import logblock_logblock_2._15.requirements.entry.blob.PaintingBlob;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for logblock-logblock-2 case 15.
 * 
 * Bug: DataOutputStream wrapping ByteArrayOutputStream should be flushed/closed
 * before calling toByteArray() on the underlying ByteArrayOutputStream.
 * 
 * This test uses dynamic execution via Driver to verify correct behavior.
 */
class LogblockLogblock2Test_15 {

    abstract static class CommonLogic {
        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("PaintingBlob write-read round trip should preserve data")
        void testPaintingBlobRoundTrip() throws Exception {
            Driver d = createDriver();
            
            // Create a PaintingBlob with test data
            PaintingBlob original = d.createPaintingBlob(1);
            original.setArt("artistic");
            original.setDirection((byte) 5);
            
            // Write to bytes and read back
            byte[] bytes = d.writeBlobToBytes(original);
            assertNotNull(bytes, "Written bytes should not be null");
            assertTrue(bytes.length > 0, "Written bytes should not be empty");
            
            // Read back and verify
            PaintingBlob restored = d.readBlobFromBytes(bytes);
            assertEquals(original.getArt(), restored.getArt(), 
                "Art should match after round trip");
            assertEquals(original.getDirection(), restored.getDirection(), 
                "Direction should match after round trip");
        }

        @Test
        @DisplayName("paintingTest should execute successfully")
        void testPaintingTestExecution() throws Exception {
            Driver d = createDriver();
            // This will throw an exception if the DataOutputStream is not properly flushed/closed
            assertDoesNotThrow(() -> d.paintingTest(),
                "paintingTest should complete without errors");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("original");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonLogic {
    //     @Override
    //     Driver createDriver() throws Exception {
    //         return new Driver("misuse");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("fixed");
        }
    }
}
