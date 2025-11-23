package apache_gora._56_1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

// モッククラスをインポート (src/main/java に配置されているはず)
/**
 * GORA-56のfollowingKey及びlastPossibleKeyのバグを再現するテストケース.
 * PartitionTestのロジックをDriver経由で実行する形式に移植.
 */
public class ApacheGoraTest_56_1 {

    // ドライバクラスの定義は外部ファイルに依存

    private static long decodeLongBigEndian(byte[] data) {
        if (data == null || data.length != 8) {
            throw new IllegalArgumentException("Decoder expects exactly 8 bytes");
        }
        long value = 0L;
        for (byte b : data) {
            value = (value << 8) | (b & 0xffL);
        }
        return value;
    }

    /**
     * 共通テストロジック PartitionTest.java のロジックを移植
     */
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();

        @BeforeEach
        void setUp() {
            // no-op (他テストとの一貫性のため)
        }

        /**
         * PartitionTest.java の encl メソッドを再現.
         * long値をバイト列にエンコードし、それをエンコーダーでデコードして返す.
         */
        private long encl(long l) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(baos);
            try {
                dos.writeLong(l);
                dos.flush();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return decodeLongBigEndian(baos.toByteArray());
        }

        @Test
        @DisplayName("followingKey produces expected successor values")
        void testFollowingKey() throws Exception {
            Driver driver = getTargetDriver();

            // 期待値の検証 (元の PartitionTest の test1() ロジックを移植)
            assertEquals(encl(0x006f000000000000L),
                (long) driver.followingKey(null, Long.class, new byte[] {0x00, 0x6f}));
            
            assertEquals(encl(1L), 
                (long) driver.followingKey(null, Long.class, new byte[] {0, 0, 0, 0, 0, 0, 0, 0}));
            
            assertEquals(encl(0x106f000000000001L), 
                (long) driver.followingKey(null, Long.class, new byte[] {0x10, 0x6f, 0, 0, 0, 0, 0, 0}));

            // 境界値テスト (元のテストから一部抜粋)
            assertEquals(encl(0xffffffffffffffffL),
                (long) driver.followingKey(null, Long.class, new byte[] {
                    (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xfe}));
            
            assertEquals(encl(0x8000000000000001L), 
                (long) driver.followingKey(null, Long.class, new byte[] {(byte) 0x80, 0, 0, 0, 0, 0, 0, 0}));

            // 不正な入力に対する例外処理の確認 (元の try-catch ロジックを再現)
            try {
                driver.followingKey(null, Long.class, new byte[] {0, 0, 0, 0, 0, 0, 0});
                fail("Should have thrown IllegalArgumentException for invalid byte array length");
            } catch (Exception e) {
                // Expected
            }
        }

        @Test
        @DisplayName("lastPossibleKey produces expected predecessor values")
        void testLastPossibleKey() throws Exception {
            Driver driver = getTargetDriver();

            // 期待値の検証 (元の PartitionTest の test2() ロジックを移植)
            assertEquals(encl(0x00ffffffffffffffL), 
                (long) driver.lastPossibleKey(null, Long.class, new byte[] {0x01}));
            
            assertEquals(encl(0x006effffffffffffL), 
                (long) driver.lastPossibleKey(null, Long.class, new byte[] {0x00, 0x6f}));
            
            assertEquals(encl(0xff6effffffffffffL), 
                (long) driver.lastPossibleKey(null, Long.class, new byte[] {(byte) 0xff, 0x6f}));

            // 不正な入力に対する例外処理の確認
            try {
                driver.lastPossibleKey(null, Long.class, new byte[] {(byte) 0, 0, 0, 0, 0, 0, 0});
                fail("Should have thrown IllegalArgumentException for invalid byte array length");
            } catch (Exception e) {
                // Expected
            }
        }
    }

    // --- 実装の切り替え ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            // 外部ドライバをインスタンス化
            return new Driver("apache_gora._56_1.original.PartitionTest");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonLogic {
//        @Override
//        Driver getTargetDriver() {
//            return new Driver("apache_gora._56_1.misuse.PartitionTest");
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver("apache_gora._56_1.fixed.PartitionTest");
        }
    }
}