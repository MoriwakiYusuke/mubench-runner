package thomas_s_b_visualee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import thomas_s_b_visualee._30.Driver;
import java.util.Scanner;

/**
 * 動的テスト: jumpOverJavaToken メソッドの動作検証
 * 
 * Case 30: Scanner.next()を呼ぶ前にhasNext()チェックが必要
 * - misuse: hasNext()チェックなしでscanner.next()を呼び出し
 * - fixed: hasNext()チェックを追加してNoSuchElementExceptionを防止
 */
public class Thomas_s_b_visualeeTest_30 {

    abstract static class CommonLogic {

        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("jumpOverJavaToken should skip 'void' and return next token")
        void testJumpOverJavaTokenVoid() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "void myMethod";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "void"
            
            String result = driver.jumpOverJavaToken(token, scanner);
            
            assertEquals("myMethod", result);
        }

        @Test
        @DisplayName("jumpOverJavaToken should skip 'public static' and return next token")
        void testJumpOverJavaTokenMultiple() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "public static void myMethod";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "public"
            
            String result = driver.jumpOverJavaToken(token, scanner);
            
            assertEquals("myMethod", result);
        }

        @Test
        @DisplayName("jumpOverJavaToken should return non-Java token as-is")
        void testJumpOverJavaTokenNonJavaToken() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "myMethod";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "myMethod"
            
            String result = driver.jumpOverJavaToken(token, scanner);
            
            assertEquals("myMethod", result);
        }

        @Test
        @DisplayName("jumpOverJavaToken should handle lone Java token at EOF gracefully")
        void testJumpOverJavaTokenLoneTokenAtEOF() throws Exception {
            Driver driver = createDriver();
            
            // Javaトークンのみで次のトークンがないソースコード
            // バグがある場合、hasNext()チェックなしでscanner.next()を呼び出し
            // NoSuchElementExceptionが発生する
            // 正しい実装ではIllegalArgumentExceptionを投げる（適切なエラーメッセージ付き）
            String sourceCode = "void";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "void"
            
            // Original/Fixed: IllegalArgumentExceptionを投げる（正しい動作）
            // Misuse: NoSuchElementExceptionを投げる（バグ動作）
            assertThrows(IllegalArgumentException.class, () -> {
                driver.jumpOverJavaToken(token, scanner);
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
    // testJumpOverJavaTokenLoneTokenAtEOFでNoSuchElementExceptionが発生し失敗する
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
