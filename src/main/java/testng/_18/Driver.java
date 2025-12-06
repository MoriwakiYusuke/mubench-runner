package testng._18;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for TestNG Case 18 - JUnitXMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_allTests.
 */
public class Driver {
    
    private final String variant;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_18/" + variant + "/JUnitXMLReporter.java");
        return Files.readString(path);
    }
    
    /**
     * Check if generateReport method exists
     */
    public boolean hasGenerateReportMethod() throws IOException {
        String source = readSourceCode();
        return source.contains("generateReport") || source.contains("void generateReport");
    }
    
    /**
     * Check if m_allTests field exists
     */
    public boolean hasAllTestsField() throws IOException {
        String source = readSourceCode();
        return source.contains("m_allTests");
    }
    
    /**
     * Check if iteration over m_allTests exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for(ITestResult tr : m_allTests)") || 
               source.contains("for (ITestResult tr : m_allTests)") ||
               source.contains("for(ITestResult tr: m_allTests)") ||
               source.contains("for(Object tr : m_allTests)") ||
               source.contains("for (Object tr : m_allTests)");
    }
    
    /**
     * Check if synchronized block is present for m_allTests iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (m_allTests)") || 
               source.contains("synchronized(m_allTests)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (m_allTests) { ... for ... m_allTests ... }
        int syncIndex = source.indexOf("synchronized (m_allTests)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(m_allTests)");
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
        
        // Check if iteration over m_allTests is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("m_allTests");
    }
}
