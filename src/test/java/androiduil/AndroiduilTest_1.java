package androiduil;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import androiduil._1.Driver;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AndroiduilTest_1 {

    abstract static class CommonLogic {

        abstract Driver getTargetDriver();
        abstract String getSourceFilePath();

        /**
         * ソースコードを検査して、Environment.getExternalStorageState() の呼び出しで
         * NullPointerException を try-catch でハンドリングしているかを確認する。
         * 
         * Original: getCacheDirectory メソッド内で直接 try-catch
         * Fixed: isExternalStorageMountedSafe() メソッドで try-catch（リファクタリング済み）
         * Misuse: ハンドリングなし → フェイル
         */
        @Test
        @DisplayName("Source code must handle NullPointerException for Environment.getExternalStorageState()")
        void testSourceCodeHandlesNullPointerException() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // NullPointerException のハンドリングがどこかにあるかチェック
            // （getCacheDirectory 内か、isExternalStorageMountedSafe 内）
            boolean hasNullPointerExceptionHandling = 
                sourceCode.contains("catch (NullPointerException") ||
                sourceCode.contains("catch(NullPointerException");
            
            assertTrue(hasNullPointerExceptionHandling, 
                "Source code must handle NullPointerException somewhere in the file. " +
                "Environment.getExternalStorageState() may throw NullPointerException.");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.original.StorageUtils.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/androiduil/_1/original/StorageUtils.java";
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.misuse.StorageUtils.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/androiduil/_1/misuse/StorageUtils.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getTargetDriver() {
            return new Driver(androiduil._1.fixed.StorageUtils.class);
        }
        
        @Override
        String getSourceFilePath() {
            return "src/main/java/androiduil/_1/fixed/StorageUtils.java";
        }
    }
}
