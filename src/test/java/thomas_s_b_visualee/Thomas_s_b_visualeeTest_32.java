package thomas_s_b_visualee;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import thomas_s_b_visualee._32.Driver;
import java.util.Scanner;

/**
 * 動的テスト: scanAfterClosedParenthesis メソッドの動作検証
 * 
 * Case 32: Scanner.next()を呼ぶ前にhasNext()チェックが必要
 * - misuse: hasNext()チェックなしでscanner.next()を呼び出し
 * - fixed: hasNext()チェックを追加してNoSuchElementExceptionを防止
 */
public class Thomas_s_b_visualeeTest_32 {

    abstract static class CommonLogic {

        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("scanAfterClosedParenthesis should return token after balanced parentheses")
        void testScanAfterClosedParenthesisBalanced() throws Exception {
            Driver driver = createDriver();
            
            // Token with balanced parentheses should just return next token
            String sourceCode = "(test) nextToken";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "(test)"
            
            String result = driver.scanAfterClosedParenthesis(token, scanner);
            
            assertEquals("nextToken", result);
        }

        @Test
        @DisplayName("scanAfterClosedParenthesis should handle nested parentheses")
        void testScanAfterClosedParenthesisNested() throws Exception {
            Driver driver = createDriver();
            
            // Unbalanced open paren, need to scan until closed
            String sourceCode = "(arg1, inner ) result";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "(arg1,"
            
            String result = driver.scanAfterClosedParenthesis(token, scanner);
            
            // Should scan past the closing parenthesis
            assertNotNull(result);
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
        @DisplayName("scanAfterClosedParenthesis should throw IllegalArgumentException when no more tokens (unbalanced)")
        void testScanAfterClosedParenthesisInsufficientTokens() throws Exception {
            Driver driver = createDriver();
            
            // Unbalanced open paren with no following tokens
            String sourceCode = "(open";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "(open"
            
            // Original version throws IllegalArgumentException for insufficient tokens
            assertThrows(IllegalArgumentException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
            });
        }

        @Test
        @DisplayName("scanAfterClosedParenthesis should throw NoSuchElementException for balanced but no next token")
        void testScanAfterClosedParenthesisBalancedNoNext() throws Exception {
            Driver driver = createDriver();
            
            // Balanced parentheses but no following token
            String sourceCode = "()";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "()"
            
            // Original version throws NoSuchElementException for balanced case with no next
            assertThrows(java.util.NoSuchElementException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
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
        @DisplayName("scanAfterClosedParenthesis throws NoSuchElementException when no more tokens (BUG)")
        void testScanAfterClosedParenthesisMisuse() throws Exception {
            Driver driver = createDriver();
            
            // Balanced parentheses but no following token
            String sourceCode = "()";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "()"
            
            // Misuse version throws NoSuchElementException (the bug)
            assertThrows(java.util.NoSuchElementException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
            });
        }

        @Test
        @DisplayName("scanAfterClosedParenthesis throws NoSuchElementException for unbalanced with insufficient tokens (BUG)")
        void testScanAfterClosedParenthesisMisuseUnbalanced() throws Exception {
            Driver driver = createDriver();
            
            // Unbalanced open paren with no following tokens
            String sourceCode = "(open";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "(open"
            
            // Misuse version throws NoSuchElementException (the bug)
            assertThrows(java.util.NoSuchElementException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
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
        @DisplayName("scanAfterClosedParenthesis should throw IllegalArgumentException when no more tokens (proper error)")
        void testScanAfterClosedParenthesisInsufficientTokensHandled() throws Exception {
            Driver driver = createDriver();
            
            // Balanced parentheses but no following token
            String sourceCode = "()";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "()"
            
            // Fixed version throws IllegalArgumentException with proper message
            assertThrows(IllegalArgumentException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
            });
        }

        @Test
        @DisplayName("scanAfterClosedParenthesis should throw IllegalArgumentException for unbalanced with insufficient tokens")
        void testScanAfterClosedParenthesisUnbalancedHandled() throws Exception {
            Driver driver = createDriver();
            
            // Unbalanced open paren with no following tokens
            String sourceCode = "(open";
            Scanner scanner = driver.getSourceCodeScanner(sourceCode);
            String token = scanner.next();  // "(open"
            
            // Fixed version throws IllegalArgumentException
            assertThrows(IllegalArgumentException.class, () -> {
                driver.scanAfterClosedParenthesis(token, scanner);
            });
        }
    }
}
