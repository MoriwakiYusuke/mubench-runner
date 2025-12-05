package adempiere;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import adempiere._1.Driver;

/**
 * 動的テスト: encrypt/decrypt のラウンドトリップで UTF-8 エンコーディングを検証。
 * 
 * バグ: encrypt() で getBytes() を引数なしで使用
 * - Original: getBytes("UTF8") → UTF-8 文字が正しく処理される
 * - Misuse: getBytes() → プラットフォーム依存で文字化けの可能性
 * 
 * 非ASCII文字（日本語等）を使ってラウンドトリップテストを行い、
 * 正しくエンコーディングが指定されているかを動的に検証する。
 */
public class AdempiereTest_1 {

    abstract static class CommonCases {

        abstract Driver driver();

        /**
         * 基本的な暗号化・復号化のラウンドトリップテスト（ASCII文字）
         */
        @Test
        @DisplayName("Round-trip encryption/decryption should work for ASCII text")
        void testRoundTripAscii() {
            Driver d = driver();
            String original = "Hello, World!";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            assertNotEquals(original, encrypted, "Encrypted should differ from original");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, "Decrypted should match original");
        }

        /**
         * 日本語文字列でのラウンドトリップテスト
         * UTF-8 エンコーディングが正しく使用されていないと失敗する
         */
        @Test
        @DisplayName("Round-trip encryption/decryption should work for Japanese text (UTF-8)")
        void testRoundTripJapanese() {
            Driver d = driver();
            String original = "こんにちは世界";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, 
                "Decrypted Japanese text should match original. " +
                "Failure indicates getBytes() is not using explicit UTF-8 encoding.");
        }

        /**
         * 中国語文字列でのラウンドトリップテスト
         */
        @Test
        @DisplayName("Round-trip encryption/decryption should work for Chinese text (UTF-8)")
        void testRoundTripChinese() {
            Driver d = driver();
            String original = "你好世界";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, 
                "Decrypted Chinese text should match original.");
        }

        /**
         * 絵文字を含む文字列でのラウンドトリップテスト
         */
        @Test
        @DisplayName("Round-trip encryption/decryption should work for emoji (UTF-8)")
        void testRoundTripEmoji() {
            Driver d = driver();
            String original = "Hello 🌍🌎🌏";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, 
                "Decrypted emoji text should match original.");
        }

        /**
         * 空文字列のテスト
         */
        @Test
        @DisplayName("Empty string should be handled correctly")
        void testEmptyString() {
            Driver d = driver();
            String original = "";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, "Empty string should round-trip correctly");
        }

        /**
         * 混合文字列（ASCII + 非ASCII）でのテスト
         */
        @Test
        @DisplayName("Round-trip should work for mixed ASCII and non-ASCII text")
        void testRoundTripMixed() {
            Driver d = driver();
            String original = "Hello こんにちは 你好 🌍";
            
            String encrypted = d.encrypt(original);
            assertNotNull(encrypted, "Encrypted value should not be null");
            
            String decrypted = d.decrypt(encrypted);
            assertEquals(original, decrypted, 
                "Mixed text should round-trip correctly.");
        }
    }

    // --- 実行定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(new adempiere._1.original.Secure());
        }
    }

    // Misuse: getBytes() を引数なしで使用 → 非ASCII文字で失敗する可能性
    // テスト確認済み: 日本語テストで失敗
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver(new adempiere._1.misuse.Secure());
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(new adempiere._1.fixed.Secure());
        }
    }
}
