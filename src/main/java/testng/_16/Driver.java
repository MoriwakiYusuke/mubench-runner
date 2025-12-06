package testng._16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Driver for TestNG Case 16 - ChronologicalPanel synchronization bug.
 * 
 * This driver uses source code static analysis to verify the presence of
 * synchronized block around the iteration of invokedMethods list.
 * 
 * Bug: Missing synchronized block when iterating over a synchronized collection.
 * Fix: Add synchronized(invokedMethods) { ... } around the sort and iteration.
 */
public class Driver {
    
    private static final String BASE_PACKAGE = "testng._16";
    private final String sourceFilePath;
    private final String variant;
    
    public Driver(String variant) {
        this.variant = variant;
        this.sourceFilePath = "src/main/java/" + BASE_PACKAGE.replace('.', '/') 
            + "/" + variant + "/ChronologicalPanel.java";
    }
    
    /**
     * Read the source code of the ChronologicalPanel class.
     */
    public String readSourceCode() throws IOException {
        return Files.readString(Paths.get(sourceFilePath));
    }
    
    /**
     * Check if the source code contains the synchronized block pattern.
     * The fix requires: synchronized (invokedMethods) { ... }
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        // Check for synchronized(invokedMethods) pattern
        return source.contains("synchronized (invokedMethods)") 
            || source.contains("synchronized(invokedMethods)");
    }
    
    /**
     * Check if the getContent method exists in the source.
     */
    public boolean hasGetContentMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("public String getContent(");
    }
    
    /**
     * Check if Collections.sort is called on invokedMethods.
     */
    public boolean hasSortCall() throws IOException {
        String source = readSourceCode();
        return source.contains("Collections.sort(invokedMethods");
    }
    
    /**
     * Check if the code iterates over invokedMethods.
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for (IInvokedMethod im : invokedMethods)");
    }
    
    /**
     * Verify the correct fix pattern:
     * - synchronized block exists
     * - Sort and iteration are inside the synchronized block
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Find the synchronized block
        int syncStart = source.indexOf("synchronized (invokedMethods)");
        if (syncStart == -1) {
            syncStart = source.indexOf("synchronized(invokedMethods)");
        }
        if (syncStart == -1) {
            return false;
        }
        
        // Find the opening brace after synchronized
        int braceStart = source.indexOf("{", syncStart);
        if (braceStart == -1) {
            return false;
        }
        
        // Find the matching closing brace
        int braceCount = 1;
        int braceEnd = braceStart + 1;
        while (braceCount > 0 && braceEnd < source.length()) {
            char c = source.charAt(braceEnd);
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            braceEnd++;
        }
        
        // Extract the synchronized block content
        String syncBlock = source.substring(braceStart, braceEnd);
        
        // Check that both sort and iteration are inside the synchronized block
        boolean hasSortInside = syncBlock.contains("Collections.sort(invokedMethods");
        boolean hasIterationInside = syncBlock.contains("for (IInvokedMethod im : invokedMethods)");
        
        return hasSortInside && hasIterationInside;
    }
    
    public String getVariant() {
        return variant;
    }
    
    public String getSourceFilePath() {
        return sourceFilePath;
    }
}
