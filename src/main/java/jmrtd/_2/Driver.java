package jmrtd._2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for jmrtd Case 2: Cipher mode misuse in PassportAuthService.doAA()
 * 
 * Bug: Using Cipher.ENCRYPT_MODE instead of Cipher.DECRYPT_MODE in doAA() method.
 * The misuse causes the Active Authentication protocol to fail because
 * the response from the card (which is encrypted with the private key)
 * should be decrypted with the public key, not encrypted again.
 * 
 * - original: uses Cipher.DECRYPT_MODE (correct)
 * - misuse: uses Cipher.ENCRYPT_MODE (BUG)
 * - fixed: LLM FAILURE - returned wrong class (SecureMessagingWrapper instead of PassportAuthService)
 * 
 * This driver provides source code analysis to verify the fix.
 */
public class Driver {

    private final String targetClassName;
    private final String sourceFilePath;

    public Driver(String targetClassName) {
        this.targetClassName = targetClassName;
        this.sourceFilePath = "src/main/java/" + targetClassName.replace('.', '/') + ".java";
    }

    /**
     * Returns the source file path for this variant.
     */
    public String getSourceFilePath() {
        return sourceFilePath;
    }

    /**
     * Reads the source code of the target class.
     */
    public String readSourceCode() throws IOException {
        Path path = Paths.get(sourceFilePath);
        if (!Files.exists(path)) {
            throw new IOException("Source file not found: " + sourceFilePath);
        }
        return Files.readString(path);
    }

    /**
     * Checks if the source file contains the PassportAuthService class.
     * This is used to detect the LLM failure case where it returned the wrong class.
     */
    public boolean containsPassportAuthService() throws IOException {
        String sourceCode = readSourceCode();
        return sourceCode.contains("class PassportAuthService");
    }

    /**
     * Checks if the doAA method uses DECRYPT_MODE (the correct mode).
     * 
     * @return true if Cipher.DECRYPT_MODE is used, false if ENCRYPT_MODE or not found
     */
    public boolean usesDecryptMode() throws IOException {
        String sourceCode = readSourceCode();
        
        // Find doAA method - accepts any return type
        int methodStart = sourceCode.indexOf("doAA(");
        if (methodStart < 0) {
            // Method not found - likely wrong class returned by LLM
            return false;
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        // Check for DECRYPT_MODE
        return methodBody.contains("Cipher.DECRYPT_MODE");
    }

    /**
     * Checks if the doAA method uses ENCRYPT_MODE (the buggy mode).
     * 
     * @return true if Cipher.ENCRYPT_MODE is used
     */
    public boolean usesEncryptMode() throws IOException {
        String sourceCode = readSourceCode();
        
        int methodStart = sourceCode.indexOf("doAA(");
        if (methodStart < 0) {
            return false;
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        return methodBody.contains("Cipher.ENCRYPT_MODE");
    }

    /**
     * Finds the approximate end of a method by counting braces.
     */
    private int findMethodEnd(String sourceCode, int methodStart) {
        int braceCount = 0;
        boolean foundFirstBrace = false;
        
        for (int i = methodStart; i < sourceCode.length(); i++) {
            char c = sourceCode.charAt(i);
            if (c == '{') {
                braceCount++;
                foundFirstBrace = true;
            } else if (c == '}') {
                braceCount--;
                if (foundFirstBrace && braceCount == 0) {
                    return i + 1;
                }
            }
        }
        return sourceCode.length();
    }

    /**
     * Returns the target class name.
     */
    public String getTargetClassName() {
        return targetClassName;
    }
}
