package alibaba_druid._2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.security.PublicKey;

public class AlibabaDruidTest_2 {

    /**
     * 共通のテストロジック. 
     * 外部の alibaba_druid.Driver クラスを使用します.
     */
    abstract static class CommonLogic {

        // 具象クラスで Driver のインスタンスを生成して返す
        abstract Driver getTargetDriver();

        // --- テストメソッド ---

        @Test
        @DisplayName("Reproduction: decrypt with IBM-JDK-style InvalidKeyException path using PublicKey should succeed")
        void testDecryptWithPublicKeyInvalidKeyPath() throws Exception {
            Driver driver = getTargetDriver();

            // Generate a fresh RSA key pair
            String[] keyPair = driver.genKeyPair(512);
            String privateKeyBase64 = keyPair[0];
            String publicKeyBase64 = keyPair[1];

            String original = "Sensitive-Password";

            // Encrypt with the private key
            String encrypted = driver.encrypt(privateKeyBase64, original);

            // Obtain PublicKey instance
            PublicKey publicKey = driver.getPublicKey(publicKeyBase64);

            // Decrypt using the PublicKey-based API
            String decrypted = driver.decrypt(publicKey, encrypted);

            assertEquals(original, decrypted,
                    "Decryption with PublicKey via IBM-JDK workaround path must correctly round-trip the plaintext");
        }

        @Test
        @DisplayName("Round-trip: default key encrypt/decrypt with String-based API")
        void testDefaultKeyRoundTripWithStringApi() throws Exception {
            Driver driver = getTargetDriver();

            String original = "DefaultKey-Trip";

            // Use default internal key (null)
            String encrypted = driver.encrypt(original);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(original, decrypted,
                    "Encrypt/Decrypt using default key and String-based API should be a lossless round-trip");
        }

        @Test
        @DisplayName("Round-trip: multi-byte UTF-8 content survives encrypt/decrypt (Short)")
        void testMultiByteUtf8RoundTrip() throws Exception {
            Driver driver = getTargetDriver();

            // 512bit RSAの上限(53byte)を超えないように短縮したマルチバイト文字列
            String original = "Pwd:€あ\uD834\uDD1E";

            String encrypted = driver.encrypt(original);
            String decrypted = driver.decrypt(encrypted);

            assertEquals(original, decrypted,
                    "Decrypting multi-byte UTF-8 text must yield the original string");
        }

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
    // ここで Driver クラスに「どの ConfigTools を操作するか」を Class オブジェクトで渡します

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Originalのクラスを渡してドライバを生成
            return new Driver(alibaba_druid._2.original.ConfigTools.class);
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Misuseのクラスを渡してドライバを生成
            return new Driver(alibaba_druid._2.misuse.ConfigTools.class);
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Fixedのクラスを渡してドライバを生成
            return new Driver(alibaba_druid._2.fixed.ConfigTools.class);
        }
    }
}