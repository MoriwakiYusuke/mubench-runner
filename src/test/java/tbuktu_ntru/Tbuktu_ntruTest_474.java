package tbuktu_ntru;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import tbuktu_ntru._474.Driver;
import java.io.ByteArrayOutputStream;

/**
 * 動的テスト: SignatureParameters.writeTo(OutputStream) の動作検証
 * 
 * Bug: DataOutputStream.flush() が呼ばれていないため、
 * バッファされたデータがOutputStreamに書き込まれない可能性がある
 */
public class Tbuktu_ntruTest_474 {

    abstract static class CommonLogic {

        abstract Driver driver() throws Exception;

        @Test
        @DisplayName("writeTo should write parameter data to output stream")
        void testWriteToOutputsData() throws Exception {
            Driver d = driver();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.writeTo(baos);
            byte[] data = baos.toByteArray();
            
            assertNotNull(data);
            assertTrue(data.length > 0, "writeTo should write data to the output stream");
        }

        @Test
        @DisplayName("writeTo should produce consistent output length")
        void testWriteToConsistentLength() throws Exception {
            Driver d = driver();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.writeTo(baos);
            int length = baos.toByteArray().length;
            
            assertTrue(length >= 56, "writeTo should write at least 56 bytes, but wrote " + length);
        }

        @Test
        @DisplayName("writeTo should write all parameters correctly")
        void testWriteToComplete() throws Exception {
            Driver d = driver();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            d.writeTo(baos);
            byte[] data = baos.toByteArray();
            
            // APR2011_439 パラメータセットの場合:
            // N=439 = 0x000001B7
            assertEquals(0x00, data[0] & 0xFF, "First byte of N should be 0x00");
            assertEquals(0x00, data[1] & 0xFF, "Second byte of N should be 0x00");
            assertEquals(0x01, data[2] & 0xFF, "Third byte of N should be 0x01");
            assertEquals(0xB7, data[3] & 0xFF, "Fourth byte of N should be 0xB7");
            
            // q=2048 = 0x00000800
            assertEquals(0x00, data[4] & 0xFF, "First byte of q should be 0x00");
            assertEquals(0x00, data[5] & 0xFF, "Second byte of q should be 0x00");
            assertEquals(0x08, data[6] & 0xFF, "Third byte of q should be 0x08");
            assertEquals(0x00, data[7] & 0xFF, "Fourth byte of q should be 0x00");
        }

        @Test
        @DisplayName("writeTo should flush data when using BufferedOutputStream")
        void testWriteToFlushesBuffer() throws Exception {
            Driver d = driver();
            int bytesWritten = d.writeToBuffered();
            
            assertTrue(bytesWritten > 0, 
                "writeTo should flush the buffer - expected > 0 bytes but got " + bytesWritten);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("original");
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("misuse");
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed");
        }
    }
}
