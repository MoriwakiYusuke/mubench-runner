package jmrtd._1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for jmrtd Case 1: DataOutputStream not closed in SecureMessagingWrapper.
 * 
 * Bug: DataOutputStream.close() not called in readDO8E() method.
 * - original: has dataOut.close() in try-finally (correct)
 * - misuse: missing dataOut.close() (BUG - resource leak)
 * - fixed: LLM correctly added try-finally with dataOut.close()
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
     * Checks if the readDO8E method properly closes the DataOutputStream.
     * 
     * The correct implementation should have:
     * - try-finally block wrapping dataOut usage
     * - dataOut.close() in the finally block
     * 
     * @return true if dataOut.close() is properly called, false otherwise
     */
    public boolean hasDataOutputStreamClose() throws IOException {
        String sourceCode = readSourceCode();
        
        // Find readDO8E method
        int methodStart = sourceCode.indexOf("private void readDO8E(");
        if (methodStart < 0) {
            throw new IllegalStateException("readDO8E method not found in source");
        }
        
        // Find the end of the method (next private/public method or end of class)
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        // Check for dataOut.close() call
        return methodBody.contains("dataOut.close()");
    }

    /**
     * Checks if the readDO8E method has a try-finally block for resource management.
     */
    public boolean hasTryFinallyBlock() throws IOException {
        String sourceCode = readSourceCode();
        
        int methodStart = sourceCode.indexOf("private void readDO8E(");
        if (methodStart < 0) {
            throw new IllegalStateException("readDO8E method not found in source");
        }
        
        int methodEnd = findMethodEnd(sourceCode, methodStart);
        String methodBody = sourceCode.substring(methodStart, methodEnd);
        
        // Check for try-finally pattern
        return methodBody.contains("try {") && methodBody.contains("} finally {");
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
