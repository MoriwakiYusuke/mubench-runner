package android_rcs_rcsjta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import android_rcs_rcsjta._1.Driver;

/**
 * 動的テスト: getContributionId() の結果一貫性を検証。
 * 
 * バグ: callId.getBytes() を引数なしで使用
 * - Original: getBytes(UTF8) → 一貫した結果
 * - Misuse: getBytes() → プラットフォーム依存で結果が変わる可能性
 * 
 * 同じ入力に対して常に同じ結果が返ることを検証し、
 * 非ASCII文字を含む入力でも正常に動作することを確認する。
 */
public class AndroidRcsRcsjtaTest_1 {

    abstract static class CommonCases {

        abstract Driver driver();

        /**
         * 基本的なContributionId生成テスト（ASCII callId）
         */
        @Test
        @DisplayName("getContributionId should return consistent result for ASCII callId")
        void testConsistentResultAscii() throws Exception {
            Driver d = driver();
            String callId = "sip:12345@example.com";
            
            String result1 = d.getContributionId(callId);
            String result2 = d.getContributionId(callId);
            
            assertNotNull(result1, "ContributionId should not be null");
            assertEquals(result1, result2, "Same callId should produce same ContributionId");
        }

        /**
         * 日本語を含むcallIdでのテスト
         */
        @Test
        @DisplayName("getContributionId should return consistent result for Japanese callId")
        void testConsistentResultJapanese() throws Exception {
            Driver d = driver();
            String callId = "sip:ユーザー@example.com";
            
            String result1 = d.getContributionId(callId);
            String result2 = d.getContributionId(callId);
            
            assertNotNull(result1, "ContributionId should not be null");
            assertEquals(result1, result2, 
                "Same Japanese callId should produce same ContributionId. " +
                "Inconsistency indicates getBytes() is not using explicit UTF-8 encoding.");
        }

        /**
         * 中国語を含むcallIdでのテスト
         */
        @Test
        @DisplayName("getContributionId should return consistent result for Chinese callId")
        void testConsistentResultChinese() throws Exception {
            Driver d = driver();
            String callId = "sip:用户@example.com";
            
            String result1 = d.getContributionId(callId);
            String result2 = d.getContributionId(callId);
            
            assertNotNull(result1, "ContributionId should not be null");
            assertEquals(result1, result2, 
                "Same Chinese callId should produce same ContributionId.");
        }

        /**
         * ContributionIdの形式テスト（16進数32文字）
         */
        @Test
        @DisplayName("getContributionId should return 32 hex characters")
        void testContributionIdFormat() throws Exception {
            Driver d = driver();
            String callId = "sip:test@example.com";
            
            String result = d.getContributionId(callId);
            
            assertNotNull(result, "ContributionId should not be null");
            assertEquals(32, result.length(), "ContributionId should be 32 characters (128 bits)");
            assertTrue(result.matches("[0-9a-f]+"), "ContributionId should be lowercase hex");
        }

        /**
         * 異なるcallIdは異なるContributionIdを生成
         */
        @Test
        @DisplayName("Different callIds should produce different ContributionIds")
        void testDifferentCallIdsDifferentResults() throws Exception {
            Driver d = driver();
            
            String result1 = d.getContributionId("sip:user1@example.com");
            String result2 = d.getContributionId("sip:user2@example.com");
            
            assertNotNull(result1);
            assertNotNull(result2);
            assertNotEquals(result1, result2, "Different callIds should produce different ContributionIds");
        }
    }

    // --- 実行定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(android_rcs_rcsjta._1.original.ContributionIdGenerator.class);
        }
    }

    // Misuse: getBytes() を引数なしで使用
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver(android_rcs_rcsjta._1.misuse.ContributionIdGenerator.class);
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(android_rcs_rcsjta._1.fixed.ContributionIdGenerator.class);
        }
    }
}
