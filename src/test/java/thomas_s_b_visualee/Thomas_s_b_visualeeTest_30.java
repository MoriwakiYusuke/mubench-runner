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
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createOriginal();
        }

        @Test
        @DisplayName("jumpOverJavaToken should throw IllegalArgumentException when no more tokens")
        void testJumpOverJavaTokenInsufficientTokens() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "void";  // Java token only, no following token
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "void"
            
            // Original version throws IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> {
                driver.jumpOverJavaToken(token, scanner);
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
        @DisplayName("jumpOverJavaToken throws NoSuchElementException when no more tokens (BUG)")
        void testJumpOverJavaTokenMisuse() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "void";  // Java token only, no following token
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "void"
            
            // Misuse version throws NoSuchElementException (the bug)
            assertThrows(java.util.NoSuchElementException.class, () -> {
                driver.jumpOverJavaToken(token, scanner);
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
        @DisplayName("jumpOverJavaToken should return current token when no more tokens (breaks gracefully)")
        void testJumpOverJavaTokenInsufficientTokensHandled() throws Exception {
            Driver driver = createDriver();
            
            String sourceCode = "void";  // Java token only, no following token
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "void"
            
            // Fixed version breaks out of loop gracefully and returns current token
            String result = driver.jumpOverJavaToken(token, scanner);
            
            assertEquals("void", result);
        }
    }
}
