package adempiere;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import adempiere._2.Driver;

public class AdempiereTest_2 {

    /**
     * 共通のテストロジック. Driver を経由してテストを実行します.
     */
    abstract static class CommonLogic {

        // ★ここが重要: SecureInterface ではなく Driver を取得するように変更
        abstract Driver getTargetDriver();

        @Test
        @DisplayName("Round-trip with ASCII string works")
        void testEncryptDecryptAsciiRoundTrip() {
            Driver driver = getTargetDriver();
            String input = "SimpleASCII123!@#";
            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "ASCII round-trip encryption/decryption should preserve the original string");
        }

        @Test
        @DisplayName("Round-trip with multi-byte UTF-8 characters works")
        void testEncryptDecryptUtf8RoundTrip() {
            Driver driver = getTargetDriver();
            // Contains characters that are multi-byte in UTF-8
            String input = "äöüÄÖÜß日本語テスト🙂";
            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "UTF-8 round-trip encryption/decryption should preserve multi-byte characters");
        }

        @Test
        @DisplayName("Different encodings: UTF-8 correctness against raw bytes behavior")
        void testEncryptUtf8ConsistencyWithExpectedCiphertext() throws Exception {
            Driver driver = getTargetDriver();
            // Choose a deterministic multi-byte string so ciphertext differs between UTF-8 and platform default
            String input = "€"; // 0xE2 0x82 0xAC in UTF-8

            // Encrypt using target (fixed code uses UTF-8, misuse uses platform default)
            String encrypted = driver.encrypt(input);

            // Independently compute expected ciphertext using DES/ECB/PKCS5Padding and UTF-8 bytes
            javax.crypto.Cipher refCipher = javax.crypto.Cipher.getInstance("DES/ECB/PKCS5Padding");
            javax.crypto.SecretKey refKey = new javax.crypto.spec.SecretKeySpec(
                    new byte[]{100, 25, 28, -122, -26, 94, -3, -26}, "DES");
            refCipher.init(javax.crypto.Cipher.ENCRYPT_MODE, refKey);
            byte[] encBytes = refCipher.doFinal(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder(encBytes.length * 2);
            for (byte b : encBytes) {
                int x = b;
                if (x < 0) {
                    x += 256;
                }
                String tmp = Integer.toHexString(x);
                if (tmp.length() == 1) {
                    sb.append("0");
                }
                sb.append(tmp);
            }
            String expectedHex = sb.toString();

            assertEquals(expectedHex, encrypted, "Ciphertext must be based on UTF-8 bytes");
        }

        @Test
        @DisplayName("Empty string encryption/decryption")
        void testEncryptDecryptEmptyString() {
            Driver driver = getTargetDriver();
            String input = "";
            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "Empty string should be preserved through encryption/decryption");
        }

        @Test
        @DisplayName("Null string treated as empty during encryption")
        void testEncryptNullAsEmptyAndDecryptBack() {
            Driver driver = getTargetDriver();
            String input = null;

            String encrypted = driver.encrypt(input);
            // In both implementations, null is converted to empty string before encryption
            String decrypted = driver.decrypt(encrypted);

            assertEquals("", decrypted, "Null input should be treated as empty string during round-trip");
        }

        @Test
        @DisplayName("Digest generation remains deterministic for UTF-8 multi-byte content")
        void testDigestDeterministicForUtf8() {
            Driver driver = getTargetDriver();
            String input = "äöüß€日本語";

            String digest1 = driver.getDigest(input);
            String digest2 = driver.getDigest(input);

            assertEquals(digest1, digest2, "Digest must be deterministic for the same UTF-8 input");
        }
    }

    // --- 以下、実行定義 ---
    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを Driver でラップして返す
            return new Driver(new adempiere._2.original.Secure());
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを Driver でラップして返す
            // パッケージ名のスペルミス修正: missuse -> misuse
            return new Driver(new adempiere._2.misuse.Secure());
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fit extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを Driver でラップして返す
            // パッケージ名の修正: fit -> fixed
            return new Driver(new adempiere._2.fixed.Secure());
        }
    }
}
