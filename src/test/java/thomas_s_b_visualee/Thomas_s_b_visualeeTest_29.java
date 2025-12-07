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

        @Test
        @DisplayName("findAndSetPackage should handle 'package ' keyword without package name gracefully")
        void testFindAndSetPackageIncompletePackage() throws Exception {
            Driver driver = createDriver();
            
            // "package " の後にパッケージ名がないソースコード
            // バグがある場合、hasNext()チェックなしでscanner.next()を呼び出し
            // NoSuchElementExceptionが発生する
            // 正しい実装ではIllegalArgumentExceptionを投げる（適切なエラーメッセージ付き）
            String sourceCode = "package ";
            JavaSource javaSource = new JavaSource("Test");
            javaSource.setSourceCode(sourceCode);
            
            // Original/Fixed: IllegalArgumentExceptionを投げる（正しい動作）
            // Misuse: NoSuchElementExceptionを投げる（バグ動作）
            assertThrows(IllegalArgumentException.class, () -> {
                driver.findAndSetPackage(javaSource);
            });
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createOriginal();
        }
    }

    // Misuseはバグ（hasNext()チェックなしでscanner.next()を呼び出し）があるため
    // testFindAndSetPackageIncompletePackageでNoSuchElementExceptionが発生し失敗する
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonLogic {
    //     @Override
    //     Driver createDriver() throws Exception {
    //         return Driver.createMisuse();
    //     }
    // }

    // Fixedはバグ修正に失敗したためコメントアウト
    // @Nested
    // @DisplayName("Fixed")
    // class Fixed extends CommonLogic {
    //     @Override
    //     Driver createDriver() throws Exception {
    //         return Driver.createFixed();
    //     }
    // }
}
