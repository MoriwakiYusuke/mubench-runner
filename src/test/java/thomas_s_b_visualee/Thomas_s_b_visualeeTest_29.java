package thomas_s_b_visualee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import thomas_s_b_visualee._29.Driver;
import thomas_s_b_visualee._29.requirements.source.entity.JavaSource;

/**
 * 動的テスト: findAndSetPackage メソッドの動作検証
 * 
 * Case 29: Scanner.next()を呼ぶ前にhasNext()チェックが必要
 * - misuse: hasNext()チェックなしでscanner.next()を呼び出し
 * - fixed: hasNext()チェックを追加してNoSuchElementExceptionを防止
 */
public class Thomas_s_b_visualeeTest_29 {

    abstract static class CommonLogic {

        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("findAndSetPackage should correctly extract package from valid source code")
        void testFindAndSetPackageValidInput() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "package com.example.test;\n\npublic class Test {}";
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            driver.findAndSetPackage(javaSource);
            
            assertEquals("com.example.test", javaSource.getPackagePath());
        }

        @Test
        @DisplayName("findAndSetPackage should handle source code without package")
        void testFindAndSetPackageNoPackage() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "public class Test {}";
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Should not throw, packagePath should remain null
            driver.findAndSetPackage(javaSource);
            
            assertNull(javaSource.getPackagePath());
        }

        @Test
        @DisplayName("findAndSetPackage should handle empty source code")
        void testFindAndSetPackageEmptySource() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "";
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Should not throw, packagePath should remain null
            driver.findAndSetPackage(javaSource);
            
            assertNull(javaSource.getPackagePath());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createOriginal();
        }

        @Test
        @DisplayName("findAndSetPackage should throw on 'package' keyword without following token")
        void testFindAndSetPackageInsufficientTokens() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "package";  // 'package' keyword only, no package name
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Original version throws IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> {
                driver.findAndSetPackage(javaSource);
            });
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createMisuse();
        }

        // TODO: LLMによる自動修正タスク用にコメントアウト
        // Misuseテストはバグを検証するため、自動修正評価時には無効化
        /*
        @Test
        @DisplayName("findAndSetPackage throws NoSuchElementException on 'package' keyword without following token (BUG)")
        void testFindAndSetPackageMisuse() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "package";  // 'package' keyword only, no package name
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Misuse version throws NoSuchElementException (the bug)
            assertThrows(java.util.NoSuchElementException.class, () -> {
                driver.findAndSetPackage(javaSource);
            });
        }
        */
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createFixed();
        }

        @Test
        @DisplayName("findAndSetPackage should not throw on 'package' keyword without following token")
        void testFindAndSetPackageInsufficientTokensHandled() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "package";  // 'package' keyword only, no package name
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Fixed version breaks out of loop gracefully
            driver.findAndSetPackage(javaSource);  // Should not throw
            
            assertNull(javaSource.getPackagePath());
        }
    }
}
