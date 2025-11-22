package adempiere._1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AdempiereTest {

    /**
     * 共通のテストロジック. SourceDriver を経由してテストを実行します.
     */
    abstract static class CommonLogic {

        // ★ここが重要: SecureInterface ではなく SourceDriver を取得するように変更
        abstract SourceDriver getTargetDriver();

        @Test
        @DisplayName("Encrypt/Decrypt must be UTF-8 safe for non-ASCII characters")
        void testEncryptDecryptUtf8RoundTrip() {
            SourceDriver driver = getTargetDriver();

            // String containing characters that differ between UTF-8 and many legacy encodings
            String input = "äöüÄÖÜß€中文テスト";

            String encrypted = driver.encrypt(input);
            assertNotNull(encrypted, "Encrypted value must not be null");
            assertFalse(encrypted.isEmpty(), "Encrypted value must not be empty");
            assertNotEquals(input, encrypted, "Encryption should not return the clear text directly");

            String decrypted = driver.decrypt(encrypted);
            assertEquals(input, decrypted, "Encryption/Decryption must preserve UTF-8 characters");
        }
    }

    // --- 以下、実行定義 ---
    @Nested
    @DisplayName("Original (正解コード)")
    class Original extends CommonLogic {

        @Override
        SourceDriver getTargetDriver() {
            // 実装クラスを SourceDriver でラップして返す
            return new SourceDriver(new adempiere._1.original.Secure());
        }
    }

    @Nested
    @DisplayName("Misuse (脆弱コード)")
    class Misuse extends CommonLogic {

        @Override
        SourceDriver getTargetDriver() {
            // 実装クラスを SourceDriver でラップして返す
            // パッケージ名のスペルミス修正: missuse -> misuse
            return new SourceDriver(new adempiere._1.misuse.Secure());
        }
    }

    @Nested
    @DisplayName("Fit (修正コード)")
    class Fit extends CommonLogic {

        @Override
        SourceDriver getTargetDriver() {
            // 実装クラスを SourceDriver でラップして返す
            // パッケージ名の修正: fit -> fixed
            return new SourceDriver(new adempiere._1.fixed.Secure());
        }
    }
}
