package adempiere;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import adempiere._1.Driver;

public class AdempiereTest_1 {

    /**
     * 共通のテストロジック. SourceDriver を経由してテストを実行します.
     */
    abstract static class CommonLogic {

        // ★ここが重要: SecureInterface ではなく SourceDriver を取得するように変更
        abstract Driver getTargetDriver();

        @Test
@DisplayName("Round-trip: standard ASCII string")
void testEncryptDecryptAscii() {
    Driver driver = getTargetDriver();
    String input = "StandardASCII123!@#";
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for ASCII input");
    String decrypted = driver.decrypt(encrypted);
    assertEquals(input, decrypted, "ASCII round-trip encryption/decryption failed");
}

@Test
@DisplayName("Round-trip: multi-byte UTF-8 string (Japanese)")
void testEncryptDecryptMultiByteJapanese() {
    Driver driver = getTargetDriver();
    // Contains 2-byte and 3-byte UTF-8 characters
    String input = "日本語テキストと絵文字無しのテスト";
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for Japanese input");
    String decrypted = driver.decrypt(encrypted);
    // Fixed code uses UTF-8 consistently so round-trip is lossless.
    // Vulnerable code may use platform default charset, causing mismatch on non-UTF-8 platforms.
    assertEquals(input, decrypted, "Multi-byte UTF-8 Japanese round-trip failed (encoding issue)");
}

@Test
@DisplayName("Round-trip: multi-byte UTF-8 string (various scripts)")
void testEncryptDecryptMultiByteMixedScripts() {
    Driver driver = getTargetDriver();
    // Mixed scripts to stress UTF-8 handling
    String input = "Καλημέρα世界 مرحبا עולם हिंदी";
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for mixed script input");
    String decrypted = driver.decrypt(encrypted);
    assertEquals(input, decrypted, "Mixed multi-byte UTF-8 round-trip failed");
}

@Test
@DisplayName("Round-trip: string with surrogate pairs (e.g., musical symbol)")
void testEncryptDecryptSurrogatePairs() {
    Driver driver = getTargetDriver();
    // Character outside BMP, represented as surrogate pair
    String input = "Music: \uD834\uDD1E end"; // MUSICAL SYMBOL G CLEF
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for surrogate pair input");
    String decrypted = driver.decrypt(encrypted);
    assertEquals(input, decrypted, "Surrogate pair UTF-8 round-trip failed");
}

@Test
@DisplayName("Round-trip: empty string")
void testEncryptDecryptEmptyString() {
    Driver driver = getTargetDriver();
    String input = "";
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for empty string");
    String decrypted = driver.decrypt(encrypted);
    assertEquals(input, decrypted, "Empty string round-trip encryption/decryption failed");
}

@Test
@DisplayName("Round-trip: null treated as empty string")
void testEncryptDecryptNullAsEmpty() {
    Driver driver = getTargetDriver();
    String input = null;
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for null input");
    String decrypted = driver.decrypt(encrypted);
    // In both implementations, null clearText is converted to "" before encrypting.
    assertEquals("", decrypted, "Null input should round-trip as empty string");
}

@Test
@DisplayName("Reproduction: same UTF-8 plaintext yields consistent ciphertext under same implementation")
void testDeterministicCipherForUtf8Text() {
    Driver driver = getTargetDriver();
    String input = "アプリケーション暗号テスト";
    String enc1 = driver.encrypt(input);
    String enc2 = driver.encrypt(input);
    assertNotNull(enc1, "First encryption must not be null");
    assertNotNull(enc2, "Second encryption must not be null");
    // For DES/ECB with fixed key and no IV, encryption of same input is deterministic.
    assertEquals(enc1, enc2, "Ciphertext for the same UTF-8 input should be deterministic");
}

@Test
@DisplayName("Reproduction: encrypt-decrypt round-trip with UTF-8 plus ASCII mix")
void testEncryptDecryptUtf8AsciiMix() {
    Driver driver = getTargetDriver();
    String input = "ユーザー:user@example.com パスワード:P@55w0rd!";
    String encrypted = driver.encrypt(input);
    assertNotNull(encrypted, "Encrypted value should not be null for UTF-8/ASCII mixed input");
    String decrypted = driver.decrypt(encrypted);
    assertEquals(input, decrypted, "UTF-8/ASCII mixed string round-trip failed");
}
    }

    // --- 以下、実行定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを SourceDriver でラップして返す
            return new Driver(new adempiere._1.original.Secure());
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを SourceDriver でラップして返す
            // パッケージ名のスペルミス修正: missuse -> misuse
            return new Driver(new adempiere._1.misuse.Secure());
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fit extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            // 実装クラスを Driver でラップして返す
            // パッケージ名の修正: fit -> fixed
            return new Driver(new adempiere._1.fixed.Secure());
        }
    }
}
