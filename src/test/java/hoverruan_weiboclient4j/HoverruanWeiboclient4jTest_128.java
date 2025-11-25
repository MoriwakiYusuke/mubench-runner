package hoverruan_weiboclient4j;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import hoverruan_weiboclient4j._128.Driver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HoverruanWeiboclient4jTest_128 {

    /**
     * 共通のテストロジック.
     * 
     * このテストは、cid(String) コンストラクタで NumberFormatException を
     * try-catch でハンドリングしてカスタムメッセージを付けているかを検証します。
     * Original: try-catch (NumberFormatException) でカスタムメッセージを付ける → テストパス
     * Misuse: 例外ハンドリングなし → テストフェイル
     */
    abstract static class CommonLogic {

        abstract Driver getTargetDriver();
        
        /**
         * 実装のソースファイルパスを返す。
         */
        abstract String getSourceFilePath();

        /**
         * ソースコードを検査して、cid(String) コンストラクタで NumberFormatException を
         * try-catch でハンドリングしているかを確認する。
         */
        @Test
        @DisplayName("Source code must handle NumberFormatException in cid method")
        void testSourceCodeHandlesNumberFormatException() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // cid(String) メソッドを探す（CoreParameters.javaの静的メソッド）
            int methodStart = sourceCode.indexOf("public static Cid cid(String");
            assertTrue(methodStart >= 0, "cid(String) method should exist in source");
            
            // メソッドの終わりを見つける
            int nextMethodStart = sourceCode.indexOf("public static", methodStart + 1);
            int methodEnd = nextMethodStart > 0 ? nextMethodStart : sourceCode.length();
            
            String methodBody = sourceCode.substring(methodStart, methodEnd);
            
            // try-catch で NumberFormatException をハンドリングしているかチェック
            boolean hasNumberFormatExceptionHandling = 
                methodBody.contains("catch (NumberFormatException") ||
                methodBody.contains("catch(NumberFormatException");
            
            assertTrue(hasNumberFormatExceptionHandling, 
                "cid(String) method must handle NumberFormatException with try-catch. " +
                "Long.parseLong may throw NumberFormatException for invalid input.");
        }
    }

    // --- 以下, 実装定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(hoverruan_weiboclient4j._128.original.CoreParameters.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/hoverruan_weiboclient4j/_128/original/CoreParameters.java";
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
            return new Driver(hoverruan_weiboclient4j._128.misuse.CoreParameters.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/hoverruan_weiboclient4j/_128/misuse/CoreParameters.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(hoverruan_weiboclient4j._128.fixed.CoreParameters.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/hoverruan_weiboclient4j/_128/fixed/CoreParameters.java";
        }
    }
}
