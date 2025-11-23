package alibaba_druid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;

public class AlibabaDruidTest_1 {

    // --- Driver Interface Definition ---
    // テストコードが必要とするすべてのメソッドを定義します
    public interface Driver {
        // Decrypt methods
        String decrypt(String cipherText) throws Exception;
        String decrypt(String publicKeyText, String cipherText) throws Exception;
        String decrypt(PublicKey publicKey, String cipherText) throws Exception;
        
        // Encrypt methods
        String encrypt(String plainText) throws Exception;
        String encrypt(String key, String plainText) throws Exception;
        String encrypt(byte[] keyBytes, String plainText) throws Exception;
        
        // Key retrieval
        PublicKey getPublicKey(String publicKeyText);
        
        // Key generation (今回エラーが出ていた箇所)
        String[] genKeyPair(int keySize) throws NoSuchAlgorithmException;
    }

    /**
     * 共通のテストロジック. 
     * Driverインターフェース経由で操作します.
     */
    abstract static class CommonLogic {

        // テスト対象のドライバを取得する抽象メソッド
        abstract Driver getTargetDriver();

        // --- テストメソッド ---

        @Test
        @DisplayName("Reproduction: decrypt with IBM-JDK-style InvalidKeyException path using PublicKey should succeed")
        void testDecryptWithPublicKeyInvalidKeyPath() throws Exception {
            Driver driver = getTargetDriver();

            // Generate a fresh RSA key pair so that public/private have valid parameters
            String[] keyPair = driver.genKeyPair(512);
            String privateKeyBase64 = keyPair[0];
            String publicKeyBase64 = keyPair[1];

            String original = "Sensitive-Password-IBM-JDK-Path";

            // Encrypt with the private key (as Base64 string)
            String encrypted = driver.encrypt(privateKeyBase64, original);

            // Obtain PublicKey instance from the generated public key string
            PublicKey publicKey = driver.getPublicKey(publicKeyBase64);

            // Decrypt using the PublicKey-based API; this is where the IBM JDK workaround logic lives
            String decrypted = driver.decrypt(publicKey, encrypted);

            assertEquals(original, decrypted,
                    "Decryption with PublicKey via IBM-JDK workaround path must correctly round-trip the plaintext");
        }

        @Test
        @DisplayName("Round-trip: default key encrypt/decrypt with String-based API")
        void testDefaultKeyRoundTripWithStringApi() throws Exception {
            Driver driver = getTargetDriver();

            String original = "DefaultKey-RoundTrip-123!@#";

            // Use default internal key (null) for both encrypt and decrypt
            String encrypted = driver.encrypt(original);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(original, decrypted,
                    "Encrypt/Decrypt using default key and String-based API should be a lossless round-trip");
        }

        // @Test
        // @DisplayName("Round-trip: multi-byte UTF-8 content survives encrypt/decrypt")
        // void testMultiByteUtf8RoundTrip() throws Exception {
        //     Driver driver = getTargetDriver();

        //     String original = "多言語パスワード-パスワード: €12345\uD834\uDD1E-русский-עברית";

        //     String encrypted = driver.encrypt(original);
        //     String decrypted = driver.decrypt(encrypted);

        //     assertEquals(original, decrypted,
        //             "Decrypting multi-byte UTF-8 text must yield the original string");
        // }

        @Test
        @DisplayName("Decrypt: empty string should return empty string")
        void testDecryptEmptyString() throws Exception {
            Driver driver = getTargetDriver();

            String result = driver.decrypt("");
            assertEquals("", result, "Decrypting an empty string must return an empty string");
        }

        @Test
        @DisplayName("Decrypt: null input should return null")
        void testDecryptNullInput() throws Exception {
            Driver driver = getTargetDriver();

            String result = driver.decrypt((String) null);
            assertNull(result, "Decrypting null must return null");
        }
    }

    // --- 以下, 実装定義 ---
    // 各パッケージの ConfigTools へ処理を委譲するドライバ実装を提供します.

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver() {
                public String decrypt(String c) throws Exception { return alibaba_druid._1.original.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._1.original.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._1.original.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._1.original.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._1.original.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._1.original.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._1.original.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._1.original.ConfigTools.genKeyPair(s); }
            };
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver() {
                public String decrypt(String c) throws Exception { return alibaba_druid._1.misuse.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._1.misuse.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._1.misuse.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._1.misuse.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._1.misuse.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._1.misuse.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._1.misuse.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._1.misuse.ConfigTools.genKeyPair(s); }
            };
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver() {
                public String decrypt(String c) throws Exception { return alibaba_druid._1.fixed.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._1.fixed.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._1.fixed.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._1.fixed.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._1.fixed.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._1.fixed.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._1.fixed.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._1.fixed.ConfigTools.genKeyPair(s); }
            };
        }
    }
}