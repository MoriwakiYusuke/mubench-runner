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
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createMisuse();
        }

    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return Driver.createFixed();
        }
    }
}
