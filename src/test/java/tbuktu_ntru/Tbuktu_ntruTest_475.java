package tbuktu_ntru;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import tbuktu_ntru._475.Driver;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * 動的テスト: SignaturePrivateKey.getEncoded() の動作検証
 * 
 * Bug: DataOutputStream.flush() が呼ばれていないため、
 * バッファされたデータがByteArrayOutputStreamに書き込まれない可能性がある
 */
public class Tbuktu_ntruTest_475 {

    /**
     * テスト用のシンプルな SignaturePrivateKey バイナリデータを作成
     */
    private static byte[] createSimpleTestBytes() throws Exception {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        
        int N = 107;
        int q = 2048;
        dos.writeShort(N);
        dos.writeShort(q);
        
        int flags = 0;
        dos.writeByte(flags);
        dos.writeFloat(1000.0f);
        dos.writeByte(1);
        
        int binary3TightBytes = (int) Math.ceil(N * 2.0 / 8);
        byte[] fBytes = new byte[binary3TightBytes];
        dos.write(fBytes);
        
        int fPrimeBytes = (int) Math.ceil(N * 11.0 / 8);
        byte[] fPrimeData = new byte[fPrimeBytes];
        dos.write(fPrimeData);
        
        dos.flush();
        return bos.toByteArray();
    }

    abstract static class CommonLogic {

        abstract Driver driver() throws Exception;

        @Test
        @DisplayName("getEncoded should return non-null byte array")
        void testGetEncodedReturnsData() throws Exception {
            Driver d = driver();
            byte[] encoded = d.getEncoded();
            
            assertNotNull(encoded, "getEncoded should return non-null byte array");
            assertTrue(encoded.length > 0, "getEncoded should return non-empty byte array");
        }

        @Test
        @DisplayName("getEncoded should produce consistent output")
        void testGetEncodedConsistent() throws Exception {
            Driver d = driver();
            
            byte[] encoded1 = d.getEncoded();
            byte[] encoded2 = d.getEncoded();
            
            assertArrayEquals(encoded1, encoded2, "Multiple calls to getEncoded should produce same result");
        }

        @Test
        @DisplayName("getEncoded output should contain header data")
        void testGetEncodedHeader() throws Exception {
            Driver d = driver();
            byte[] encoded = d.getEncoded();
            
            // N=107 = 0x006B as short
            assertEquals(0x00, encoded[0] & 0xFF, "First byte of N should be 0x00");
            assertEquals(0x6B, encoded[1] & 0xFF, "Second byte of N should be 0x6B (107)");
            
            // q=2048 = 0x0800 as short
            assertEquals(0x08, encoded[2] & 0xFF, "First byte of q should be 0x08");
            assertEquals(0x00, encoded[3] & 0xFF, "Second byte of q should be 0x00");
        }

        @Test
        @DisplayName("getEncoded should call flush() or close() before toByteArray()")
        void testGetEncodedCallsFlushOrClose() throws Exception {
            Driver d = driver();
            assertTrue(d.hasFlushOrCloseInGetEncoded(), 
                "getEncoded() should call flush() or close() before toByteArray()");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("original", createSimpleTestBytes());
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("misuse", createSimpleTestBytes());
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed", createSimpleTestBytes());
        }
    }
}
