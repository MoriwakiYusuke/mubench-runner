package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Ivantrendafilov_confuciusTest_93 {

    abstract static class CommonLogic {

        abstract String getSourceFilePath();

        @Test
        @DisplayName("Source code must handle NumberFormatException in getByteValue method")
        void testSourceCodeHandlesNumberFormatException() throws Exception {
            String sourceFilePath = getSourceFilePath();
            Path path = Paths.get(sourceFilePath);
            
            assertTrue(Files.exists(path), "Source file should exist: " + sourceFilePath);
            
            String sourceCode = Files.readString(path);
            
            int methodStart = sourceCode.indexOf("public byte getByteValue(String key)");
            assertTrue(methodStart >= 0, "getByteValue(String) method should exist in source");
            
            int nextMethodStart = sourceCode.indexOf("public", methodStart + 1);
            int methodEnd = nextMethodStart > 0 ? nextMethodStart : sourceCode.length();
            
            String methodBody = sourceCode.substring(methodStart, methodEnd);
            
            boolean hasNumberFormatExceptionHandling = 
                methodBody.contains("catch (NumberFormatException") ||
                methodBody.contains("catch(NumberFormatException");
            
            assertTrue(hasNumberFormatExceptionHandling, 
                "getByteValue method must handle NumberFormatException with try-catch.");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_93/original/AbstractConfiguration.java";
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_93/misuse/AbstractConfiguration.java";
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        String getSourceFilePath() {
            return "src/main/java/ivantrendafilov_confucius/_93/fixed/AbstractConfiguration.java";
        }
    }
}
