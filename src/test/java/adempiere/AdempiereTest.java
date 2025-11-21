package adempiere;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

public class AdempiereTest {

    /**
     * 共通のテストロジック.
     * ここに書いたテストが Original/Misuse/Fit 全員に対して実行されます.
     */
    abstract static class CommonLogic {
        
        // 子クラスに「テスト対象の実体(SecureInterface)」を持ってこさせる
        abstract SecureInterface getTarget();

        @Test
        @DisplayName("暗号化と復号が正しく機能すること")
        void testEncryptDecrypt() {
            SecureInterface secure = getTarget();
            String original = "SecretData123!";
            
            // 1. 暗号化
            String encrypted = secure.encrypt(original);
            
            // nullチェック
            assertNotNull(encrypted, "暗号化結果がnullです");
            
            // 脆弱性チェック: 平文のまま保存されていないか？
            assertNotEquals(original, encrypted, "暗号化されていません(平文のままです) - 脆弱性あり");

            // 2. 復号
            String decrypted = secure.decrypt(encrypted);
            
            // 整合性チェック
            assertEquals(original, decrypted, "復号結果が元の文字列と一致しません");
            
            // ログ出力
            System.out.println("[" + secure.getClass().getName() + "]");
            System.out.println("  Original : " + original);
            System.out.println("  Encrypted: " + encrypted);
            System.out.println("  Decrypted: " + decrypted);
            System.out.println("--------------------------------------------------");
        }

        @Test
        @DisplayName("ハッシュ生成(Digest)の確認")
        void testDigest() {
            SecureInterface secure = getTarget();
            String input = "Admin";
            String digest = secure.getDigest(input);
            
            assertNotNull(digest);
            // MD5は通常32文字のHex文字列
            assertEquals(32, digest.length(), "MD5ハッシュの長さが不正です(32文字であるべき)");
            
            System.out.println("[" + secure.getClass().getName() + "]");
            System.out.println("  Input : " + input);
            System.out.println("  Digest: " + digest);
            System.out.println("--------------------------------------------------");
        }
    }

    // --- 以下、実行定義 ---

    @Nested
    @DisplayName("Original (正解コード)")
    class Original extends CommonLogic {
        @Override
        SecureInterface getTarget() {
            return new adempiere.original.Secure();
        }
    }

    @Nested
    @DisplayName("Misuse (脆弱コード)")
    class Misuse extends CommonLogic {
        @Override
        SecureInterface getTarget() {
            // ★修正: misuse -> missuse (sが2つ)
            return new adempiere.missuse.Secure();
        }
    }

    @Nested
    @DisplayName("Fit (修正コード)")
    class Fit extends CommonLogic {
        @Override
        SecureInterface getTarget() {
            // ★修正: fit -> fixed
            return new adempiere.fixed.Secure();
        }
    }
}