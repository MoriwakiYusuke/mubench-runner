package adempiere;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;
import adempiere._2.Driver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AdempiereTest_2 {

    /**
     * 共通のテストロジック. Driver を経由してテストを実行します.
     * 
     * このテストは、encrypt メソッドが明示的に UTF-8 エンコーディングを使用しているかを検証します。
     * Original: getBytes("UTF8") を使用 → テストパス
     * Misuse: getBytes() を使用（プラットフォームデフォルト） → ソースコード検査でフェイル
     */
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();
        
        /**
         * 実装のソースファイルパスを返す。
         */
        abstract String getSourceFilePath();

        /**
         * ソースコードを検査して、encrypt メソッドで getBytes() が明示的に UTF-8 を指定しているかを確認する。
         */
        @Test
        @DisplayName("Source code must use explicit UTF-8 encoding in encrypt method")
        void testSourceCodeUsesExplicitUtf8Encoding() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // encrypt メソッドの範囲を抽出
            int encryptMethodStart = sourceCode.indexOf("public String encrypt (String value)");
            assertTrue(encryptMethodStart >= 0, "encrypt method should exist in source");
            
            int encryptMethodEnd = sourceCode.indexOf("}	//	encrypt", encryptMethodStart);
            if (encryptMethodEnd < 0) {
                encryptMethodEnd = sourceCode.indexOf("}\t//\tencrypt", encryptMethodStart);
            }
            if (encryptMethodEnd < 0) {
                encryptMethodEnd = sourceCode.indexOf("}  //  encrypt", encryptMethodStart);
            }
            assertTrue(encryptMethodEnd > encryptMethodStart, "encrypt method end should be found");
            
            String encryptMethodBody = sourceCode.substring(encryptMethodStart, encryptMethodEnd);
            
            boolean hasGetBytes = encryptMethodBody.contains(".getBytes(");
            
            if (hasGetBytes) {
                boolean usesUtf8 = encryptMethodBody.contains("getBytes(\"UTF8\")") ||
                                   encryptMethodBody.contains("getBytes(\"UTF-8\")") ||
                                   encryptMethodBody.contains("getBytes(StandardCharsets.UTF_8)") ||
                                   encryptMethodBody.contains("getBytes(java.nio.charset.StandardCharsets.UTF_8)");
                
                boolean hasGetBytesNoArgs = encryptMethodBody.matches("(?s).*\\.getBytes\\(\\).*");
                
                assertTrue(usesUtf8 && !hasGetBytesNoArgs, 
                    "encrypt method must use getBytes with explicit UTF-8 encoding.");
            }
        }
    }

    // --- 以下、実行定義 ---
    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            return new Driver(new adempiere._2.original.Secure());
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/adempiere/_2/original/Secure.java";
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
            return new Driver(new adempiere._2.misuse.Secure());
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/adempiere/_2/misuse/Secure.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {

        @Override
        Driver getTargetDriver() {
            return new Driver(new adempiere._2.fixed.Secure());
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/adempiere/_2/fixed/Secure.java";
        }
    }
}
