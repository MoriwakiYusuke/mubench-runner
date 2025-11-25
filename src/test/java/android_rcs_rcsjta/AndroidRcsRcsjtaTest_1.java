package android_rcs_rcsjta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import android_rcs_rcsjta._1.Driver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AndroidRcsRcsjtaTest_1 {

    /**
     * 共通のテストロジック.
     * 
     * このテストは、getContributionId メソッドで getBytes() が明示的に UTF-8 を
     * 指定しているかを検証します。
     * Original: getBytes(UTF8) を使用 → テストパス
     * Misuse: getBytes() を引数なしで使用 → テストフェイル
     */
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();
        
        /**
         * 実装のソースファイルパスを返す。
         */
        abstract String getSourceFilePath();

        /**
         * ソースコードを検査して、getContributionId メソッドで getBytes() が
         * 明示的に UTF-8 を指定しているかを確認する。
         */
        @Test
        @DisplayName("Source code must use explicit UTF-8 encoding in getContributionId method")
        void testSourceCodeUsesExplicitUtf8Encoding() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // getContributionId メソッドを探す（public synchronized static にも対応）
            int methodStart = sourceCode.indexOf("String getContributionId(");
            assertTrue(methodStart >= 0, "getContributionId method should exist in source");
            
            // メソッドの終わりを見つける
            int nextMethodStart = sourceCode.indexOf("public", methodStart + 1);
            int methodEnd = nextMethodStart > 0 ? nextMethodStart : sourceCode.length();
            
            String methodBody = sourceCode.substring(methodStart, methodEnd);
            
            // getBytes の使用をチェック
            boolean hasGetBytes = methodBody.contains(".getBytes(");
            
            if (hasGetBytes) {
                // getBytes が使用されている場合、UTF-8 が明示的に指定されているかチェック
                boolean usesUtf8 = methodBody.contains("getBytes(UTF8)") ||
                                   methodBody.contains("getBytes(\"UTF8\")") ||
                                   methodBody.contains("getBytes(\"UTF-8\")") ||
                                   methodBody.contains("getBytes(StandardCharsets.UTF_8)");
                
                // getBytes() が引数なしで呼ばれていないかチェック
                boolean hasGetBytesNoArgs = methodBody.matches("(?s).*\\.getBytes\\(\\).*");
                
                assertTrue(usesUtf8 && !hasGetBytesNoArgs, 
                    "getContributionId method must use getBytes with explicit UTF-8 encoding. " +
                    "Found getBytes without explicit charset specification.");
            }
        }
    }

    // --- 以下, 実装定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(android_rcs_rcsjta._1.original.ContributionIdGenerator.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/android_rcs_rcsjta/_1/original/ContributionIdGenerator.java";
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
            return new Driver(android_rcs_rcsjta._1.misuse.ContributionIdGenerator.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/android_rcs_rcsjta/_1/misuse/ContributionIdGenerator.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(android_rcs_rcsjta._1.fixed.ContributionIdGenerator.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/android_rcs_rcsjta/_1/fixed/ContributionIdGenerator.java";
        }
    }
}
