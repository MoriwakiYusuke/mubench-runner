package android_rcs_rcsjta._1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Arrays;

import java.security.PublicKey;

public class AndroidRcsRcsjtaTest_1 {

    /**
     * 共通のテストロジック. 
     * 外部の alibaba_druid.Driver クラスを使用します.
     */
    abstract static class CommonLogic {

        // 具象クラスで Driver のインスタンスを生成して返す
        abstract Driver getTargetDriver();

        // --- テストメソッド ---

               @Test
        @DisplayName("Deterministic HMAC for ASCII input regardless of default charset")
        void testAsciiCallIdDeterministic() throws Exception {
            Driver driver = getTargetDriver();

            String callId = "simple-call-id-12345";

            String id1 = driver.getContributionId(callId);
            String id2 = driver.getContributionId(callId);

            // For a fixed callId and fixed secretKey, result must be stable
            assertNotNull(id1);
            assertEquals(id1, id2, "Contribution ID for ASCII callId must be deterministic");
        }

        @Test
        @DisplayName("Same UTF-8 multibyte callId must yield same ID even if default charset changes")
        void testMultibyteCallIdConsistency() throws Exception {
            Driver driver = getTargetDriver();

            // Contains multi-byte characters under UTF-8
            String callId = "通話-ID-äöü-ß-ñ";

            String id1 = driver.getContributionId(callId);

            // Simulate environment change that would affect String.getBytes()
            // in vulnerable implementation by temporarily switching system property.
            String originalEncoding = System.getProperty("file.encoding");
            try {
                System.setProperty("file.encoding", "ISO-8859-1");
                // Force re-computation under new default-charset environment
                String id2 = driver.getContributionId(callId);

                assertNotNull(id1);
                assertNotNull(id2);
                // Fixed code uses UTF-8 explicitly → stable; misuse relies on default charset → may differ
                assertEquals(id1, id2,
                        "Contribution ID for multibyte callId must be independent of default charset");
            } finally {
                if (originalEncoding != null) {
                    System.setProperty("file.encoding", originalEncoding);
                }
            }
        }

        @Test
        @DisplayName("Empty callId yields deterministic fixed-length hex string")
        void testEmptyCallIdDeterministicLength() throws Exception {
            Driver driver = getTargetDriver();

            String callId = "";

            String id1 = driver.getContributionId(callId);
            String id2 = driver.getContributionId(callId);

            assertNotNull(id1);
            assertEquals(32, id1.length(), "Contribution ID must be 128 bits represented as 32 hex chars");
            assertEquals(id1, id2, "Contribution ID for empty callId must be deterministic");
        }

        @Test
        @DisplayName("UTF-8 multibyte callId with emojis handled consistently")
        void testEmojiCallIdUtf8Handling() throws Exception {
            Driver driver = getTargetDriver();

            // Include surrogate pairs / 4-byte UTF-8 sequences
            String callId = "call-📞-チャット-😀";

            String idUtf8Baseline = driver.getContributionId(callId);

            // Compute again after simulating an environment where default charset could be non-UTF-8
            String originalEncoding = System.getProperty("file.encoding");
            try {
                System.setProperty("file.encoding", "US-ASCII");
                String idUtf8Second = driver.getContributionId(callId);

                assertNotNull(idUtf8Baseline);
                assertNotNull(idUtf8Second);
                // Correct implementation must not depend on default charset
                assertEquals(idUtf8Baseline, idUtf8Second,
                        "Contribution ID for emoji/multibyte callId must not depend on default charset");
            } finally {
                if (originalEncoding != null) {
                    System.setProperty("file.encoding", originalEncoding);
                }
            }
        }

        @Test
        @DisplayName("Secret key stability and impact on Contribution ID")
        void testSecretKeyStabilityAndEffect() throws Exception {
            Driver driver = getTargetDriver();

            String callId = "key-test-call-id";

            // Capture original secret key and resulting ID
            byte[] originalKey = driver.getSecretKey();
            String originalId = driver.getContributionId(callId);

            assertNotNull(originalKey);
            assertNotNull(originalId);

            // Change secret key explicitly and ensure ID changes
            byte[] newKey = new byte[16];
            Arrays.fill(newKey, (byte) 0x7F);
            driver.setSecretKey(newKey);

            String newId = driver.getContributionId(callId);
            assertNotNull(newId);
            assertNotEquals(originalId, newId, "Changing secret key must change Contribution ID");

            // Restore original key and ensure original ID is recovered
            driver.setSecretKey(originalKey);
            String restoredId = driver.getContributionId(callId);
            assertEquals(originalId, restoredId,
                    "Restoring original secret key must restore original Contribution ID");
        }
    }

    // --- 以下, 実装定義 ---
    // ここで Driver クラスに「どの ContributionIdGenerator を操作するか」を Class オブジェクトで渡します

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Originalのクラスを渡してドライバを生成
            return new Driver(android_rcs_rcsjta._1.original.ContributionIdGenerator.class);
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Misuseのクラスを渡してドライバを生成
            return new Driver(android_rcs_rcsjta._1.misuse.ContributionIdGenerator.class);
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // Fixedのクラスを渡してドライバを生成
            return new Driver(android_rcs_rcsjta._1.fixed.ContributionIdGenerator.class);
        }
    }
}