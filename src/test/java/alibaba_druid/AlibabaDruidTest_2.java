package alibaba_druid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.security.PublicKey;
import java.security.NoSuchAlgorithmException;

public class AlibabaDruidTest_2 {

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
        @DisplayName("Reproduction: encrypt/decrypt round-trip works on JVMs that throw InvalidKeyException")
        void testEncryptDecryptRoundTripWithKeyInitFailureHandling() throws Exception {
            Driver driver = getTargetDriver();
            String input = "SimpleTestPassword123!";

            // The vulnerable implementation fails to handle InvalidKeyException in encrypt(byte[], String),
            // which is triggered on some JVMs (e.g., IBM JDK) when using a private key for ENCRYPT_MODE.
            // The fixed code falls back to a compatible key type, so round-trip succeeds.
            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "Round-trip encryption/decryption must preserve the original text");
        }

        @Test
        @DisplayName("Reproduction: encrypt/decrypt supports multi-byte UTF-8 characters")
        void testEncryptDecryptMultiByteWithKeyInitFailureHandling() throws Exception {
            Driver driver = getTargetDriver();
            String input = "多字节-パスワード-🔐-€-𝄞";

            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "Multi-byte UTF-8 content must survive encrypt/decrypt round-trip");
        }

        @Test
        @DisplayName("Reproduction: encrypt/decrypt empty string")
        void testEncryptDecryptEmptyStringWithKeyInitFailureHandling() throws Exception {
            Driver driver = getTargetDriver();
            String input = "";

            String encrypted = driver.encrypt(input);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(input, decrypted, "Empty string should round-trip through encryption/decryption");
        }

        @Test
        @DisplayName("Reproduction: encrypt/decrypt with custom generated RSA keypair")
        void testEncryptDecryptWithGeneratedKeyPairAndKeyInitFailureHandling() throws Exception {
            Driver driver = getTargetDriver();

            // Generate a fresh RSA keypair and use its private key for encryption
            String[] keyPair = driver.genKeyPair(512); // [0] = private (Base64), [1] = public (Base64)
            String privateKeyBase64 = keyPair[0];
            String publicKeyBase64 = keyPair[1];

            String input = "CustomKeyPair-Test-密码";

            // Use the custom private key for encryption
            String encrypted = driver.encrypt(privateKeyBase64, input);

            // Decrypt using the corresponding public key
            String decrypted = driver.decrypt(publicKeyBase64, encrypted);

            assertEquals(input, decrypted,
                    "Encrypt/decrypt with a dynamically generated keypair must correctly handle key init failures");
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
                public String decrypt(String c) throws Exception { return alibaba_druid._2.original.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._2.original.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._2.original.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._2.original.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._2.original.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._2.original.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._2.original.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._2.original.ConfigTools.genKeyPair(s); }
            };
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver() {
                public String decrypt(String c) throws Exception { return alibaba_druid._2.misuse.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._2.misuse.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._2.misuse.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._2.misuse.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._2.misuse.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._2.misuse.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._2.misuse.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._2.misuse.ConfigTools.genKeyPair(s); }
            };
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver() {
                public String decrypt(String c) throws Exception { return alibaba_druid._2.fixed.ConfigTools.decrypt(c); }
                public String decrypt(String p, String c) throws Exception { return alibaba_druid._2.fixed.ConfigTools.decrypt(p, c); }
                public String decrypt(PublicKey k, String c) throws Exception { return alibaba_druid._2.fixed.ConfigTools.decrypt(k, c); }
                public String encrypt(String p) throws Exception { return alibaba_druid._2.fixed.ConfigTools.encrypt(p); }
                public String encrypt(String k, String p) throws Exception { return alibaba_druid._2.fixed.ConfigTools.encrypt(k, p); }
                public String encrypt(byte[] k, String p) throws Exception { return alibaba_druid._2.fixed.ConfigTools.encrypt(k, p); }
                public PublicKey getPublicKey(String k) { return alibaba_druid._2.fixed.ConfigTools.getPublicKey(k); }
                public String[] genKeyPair(int s) throws NoSuchAlgorithmException { return alibaba_druid._2.fixed.ConfigTools.genKeyPair(s); }
            };
        }
    }
}