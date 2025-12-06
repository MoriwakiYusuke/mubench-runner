package testng._22;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for TestNG Case 22 - XMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over results map.
 */
public class Driver {
    
    private final String variant;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_22/" + variant + "/XMLReporter.java");
        return Files.readString(path);
    }
    
    /**
     * Check if getSuiteAttributes method exists
     */
    public boolean hasGetSuiteAttributesMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("getSuiteAttributes") || source.contains("Properties getSuiteAttributes");
    }
    
    /**
     * Check if results field/variable exists
     */
    public boolean hasResultsVariable() throws IOException {
        String source = readSourceCode();
        return source.contains("results") && source.contains("getResults(");
    }
    
    /**
     * Check if iteration over results exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("results.entrySet()") || 
               source.contains("for (Map.Entry") ||
               source.contains("for(Map.Entry");
    }
    
    /**
     * Check if synchronized block is present for results iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (results)") || 
               source.contains("synchronized(results)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (results) { ... results.entrySet() ... }
        int syncIndex = source.indexOf("synchronized (results)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(results)");
        }
        if (syncIndex == -1) {
            return false;
        }
        
        // Find the opening brace after synchronized
        int braceStart = source.indexOf("{", syncIndex);
        if (braceStart == -1) {
            return false;
        }
        
        // Find matching closing brace
        int braceCount = 1;
        int braceEnd = braceStart + 1;
        while (braceEnd < source.length() && braceCount > 0) {
            char c = source.charAt(braceEnd);
            if (c == '{') braceCount++;
            if (c == '}') braceCount--;
            braceEnd++;
        }
        
        // Check if iteration over results is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("results.entrySet()") || syncBlock.contains("results");
    }
}
