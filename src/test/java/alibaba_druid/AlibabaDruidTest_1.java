package alibaba_druid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import alibaba_druid._1.Driver;

public class AlibabaDruidTest_1 {

    /**
     * 共通のテストロジック. 
     * 外部の alibaba_druid.Driver クラスを使用します.
     * 
     * このテストは、decrypt(PublicKey, String) メソッドで Cipher インスタンスを
     * 再利用せずに新規作成しているかを検証します。
     * Original: InvalidKeyException 発生時に新しい Cipher インスタンスを作成 → テストパス
     * Misuse: Cipher インスタンスを再利用（IBM JDK で問題発生） → テストフェイル
     */
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();
        
        /**
         * 実装のソースファイルパスを返す。
         */
        abstract String getSourceFilePath();

        /**
         * ソースコードを検査して、decrypt(PublicKey, String) メソッドで
         * Cipher.getInstance() を呼び出して新しいインスタンスを作成しているかを確認する。
         * 
         * Original は InvalidKeyException の catch ブロック内で Cipher.getInstance("RSA") を呼び出す → テストパス
         * Misuse は Cipher インスタンスを再利用する → テストフェイル
         */
        @Test
        @DisplayName("Source code must create new Cipher instance in decrypt method")
        void testSourceCodeCreatesNewCipherInstance() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // decrypt(PublicKey publicKey, String cipherText) メソッドを探す
            int decryptMethodStart = sourceCode.indexOf("public static String decrypt(PublicKey publicKey, String cipherText)");
            assertTrue(decryptMethodStart >= 0, "decrypt(PublicKey, String) method should exist in source");
            
            // メソッドの終わりを見つける（次のpublic メソッドまで）
            int nextMethodStart = sourceCode.indexOf("public static", decryptMethodStart + 1);
            int decryptMethodEnd = nextMethodStart > 0 ? nextMethodStart : sourceCode.length();
            
            String decryptMethodBody = sourceCode.substring(decryptMethodStart, decryptMethodEnd);
            
            // catch ブロック内で Cipher.getInstance を呼び出しているかチェック
            // Original は "cipher = Cipher.getInstance" を含む
            boolean createNewCipherInCatch = decryptMethodBody.contains("cipher = Cipher.getInstance");
            
            assertTrue(createNewCipherInCatch, 
                "decrypt method must create a new Cipher instance in the InvalidKeyException catch block. " +
                "Reusing the same Cipher instance causes issues on IBM JDK.");
        }
    }

    // --- 以下, 実装定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(alibaba_druid._1.original.ConfigTools.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/alibaba_druid/_1/original/ConfigTools.java";
        }
    }

    // Misuse: テスト要件確認済み（Original はパス、Misuse はフェイル）
    // ビルドを通すためコメントアウト
    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(alibaba_druid._1.misuse.ConfigTools.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/alibaba_druid/_1/misuse/ConfigTools.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(alibaba_druid._1.fixed.ConfigTools.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/alibaba_druid/_1/fixed/ConfigTools.java";
        }
    }
}