package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ivantrendafilov_confuciusTest_97 {

    abstract static class CommonLogic {

        abstract String getSourceFilePath();

        @Test
        @DisplayName("Source code must handle NumberFormatException in getLongValue(String, long) method")
        void testSourceCodeHandlesNumberFormatException() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            // synchronized キーワードを含まない形で検索
            int methodStart = sourceCode.indexOf("long getLongValue(String key, long defaultValue)");
            assertTrue(methodStart >= 0, "getLongValue(String, long) method should exist in source");
            
            int nextMethodStart = sourceCode.indexOf("public", methodStart + 1);
            int methodEnd = nextMethodStart > 0 ? nextMethodStart : sourceCode.length();
            
            String methodBody = sourceCode.substring(methodStart, methodEnd);
            
            boolean hasNumberFormatExceptionHandling = 
                methodBody.contains("catch (NumberFormatException") ||
                methodBody.contains("catch(NumberFormatException");
            
            assertTrue(hasNumberFormatExceptionHandling, 
                "getLongValue(String, long) method must handle NumberFormatException with try-catch.");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_97/original/AbstractConfiguration.java";
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_97/misuse/AbstractConfiguration.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_97/fixed/AbstractConfiguration.java";
        }
    }
}
