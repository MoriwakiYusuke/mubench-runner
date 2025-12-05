package rhino;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import rhino._1.Driver;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * 動的テスト: Parserのfunction()メソッドが正しくinitFunctionを呼び出すか検証。
 * 
 * バグ: IRFactory.initFunction()が同一実行パスで2回呼び出される
 * - Original: 1回のみ呼び出し → パース成功
 * - Misuse: 2回呼び出し → 無限ループ（StackOverflowError）
 * 
 * 動的テストではネストした関数定義をパースし、タイムアウトで無限ループを検出する。
 */
class RhinoTest_1 {

    // 無限ループ検出用のタイムアウト（秒）
    private static final int PARSE_TIMEOUT_SECONDS = 5;

    abstract static class CommonCases {
        abstract Driver driver() throws Exception;

        /**
         * 単純な関数定義のパーステスト
         */
        @Test
        @DisplayName("Should parse simple function definition")
        @Timeout(value = PARSE_TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
        void testParseSimpleFunction() throws Exception {
            Driver d = driver();
            String code = "function foo() { return 1; }";
            
            Object result = d.parse(code, "test.js", 1);
            assertNotNull(result, "Parser should return non-null result");
        }

        /**
         * ネストした関数定義のパーステスト
         * バグがある場合、この関数定義で無限ループが発生する
         */
        @Test
        @DisplayName("Should parse nested function definition without infinite loop")
        @Timeout(value = PARSE_TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
        void testParseNestedFunction() throws Exception {
            Driver d = driver();
            // ネストした関数定義 - バグがあると無限ループ
            String code = "function outer() { function inner() { return 1; } return inner(); }";
            
            Object result = d.parse(code, "test.js", 1);
            assertNotNull(result, "Parser should return non-null result for nested functions");
        }

        /**
         * 複数のネストレベルを持つ関数定義のパーステスト
         */
        @Test
        @DisplayName("Should parse deeply nested function definitions")
        @Timeout(value = PARSE_TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
        void testParseDeeplyNestedFunctions() throws Exception {
            Driver d = driver();
            String code = 
                "function a() { " +
                "  function b() { " +
                "    function c() { return 1; } " +
                "    return c(); " +
                "  } " +
                "  return b(); " +
                "}";
            
            Object result = d.parse(code, "test.js", 1);
            assertNotNull(result, "Parser should handle deeply nested functions");
        }

        /**
         * 関数式（function expression）のパーステスト
         */
        @Test
        @DisplayName("Should parse function expressions")
        @Timeout(value = PARSE_TIMEOUT_SECONDS, unit = TimeUnit.SECONDS)
        void testParseFunctionExpression() throws Exception {
            Driver d = driver();
            String code = "var f = function() { var g = function() { return 1; }; return g(); };";
            
            Object result = d.parse(code, "test.js", 1);
            assertNotNull(result, "Parser should handle function expressions");
        }

        /**
         * 静的解析: initFunctionが1回だけ呼ばれるパターンか確認（後方互換性のため）
         */
        @Test
        @DisplayName("Source should have correct initFunction pattern (static check)")
        void testInitFunctionCalledOnce() throws Exception {
            Driver d = driver();
            assertTrue(d.hasCorrectInitFunctionPattern(),
                "initFunction should be called exactly once per function");
        }
    }

    // --- 実行定義 ---

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("original");
        }
    }

    // Misuse: initFunctionが2回呼ばれる → 無限ループでタイムアウト
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() throws Exception {
    //         return new Driver("misuse");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed");
        }
    }
}
