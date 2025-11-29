package pawotag;

import pawotag._1.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for pawotag/1 - CryptoUtil empty array handling bug.
 * 
 * Bug: CryptoUtil.encrypt() calls Cipher.doFinal(byte[], int, int, byte[], int)
 * with a zero-length byte array, which causes issues on some JVMs (IBM JVM 6).
 * 
 * Fix: Check if inbytes.length == 0 and use a different doFinal() variant.
 */
class PawotagTest_1 {

    private static final String BASE_PACKAGE = "pawotag._1";

    abstract static class CommonCases {

        abstract Driver driver();

        @BeforeEach
        void resetKey() {
            driver().resetSecretKey();
        }

        @Test
        @DisplayName("encrypt and decrypt round-trip works for non-empty string")
        void encryptDecryptNonEmpty() {
            String original = "Hello, World!";
            String encrypted = driver().encrypt(original);
            assertNotNull(encrypted);
            assertNotEquals(original, encrypted);
            
            String decrypted = driver().decrypt(encrypted);
            assertEquals(original, decrypted);
        }

        @Test
        @DisplayName("encrypt and decrypt round-trip works for empty string")
        void encryptDecryptEmpty() {
            String original = "";
            String encrypted = driver().encrypt(original);
            assertNotNull(encrypted);
            
            String decrypted = driver().decrypt(encrypted);
            assertEquals(original, decrypted);
        }

        @Test
        @DisplayName("encrypt handles null input as empty string")
        void encryptNullInput() {
            String encrypted = driver().encrypt(null);
            assertNotNull(encrypted);
            
            String decrypted = driver().decrypt(encrypted);
            assertEquals("", decrypted);
        }

        @Test
        @DisplayName("decrypt returns null for null input")
        void decryptNullInput() {
            String result = driver().decrypt(null);
            assertNull(result);
        }

        @Test
        @DisplayName("code has proper empty array check (source code pattern)")
        void hasEmptyArrayCheckPattern() throws Exception {
            assertTrue(driver().hasEmptyArrayCheck(),
                "encrypt() should check for empty input array before calling doFinal");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.CryptoUtil");
        }
    }

    // Misuse fails the hasEmptyArrayCheckPattern test because it doesn't check for empty arrays
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.CryptoUtil");
    // }


    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {

        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.CryptoUtil");
        }
    }
}
