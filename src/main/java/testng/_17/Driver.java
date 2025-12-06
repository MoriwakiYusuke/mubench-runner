package testng._17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Driver for TestNG Case 17 - JUnitXMLReporter synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over m_configIssues.
 */
public class Driver {
    
    private final String variant;
    
    public Driver(String variant) {
        this.variant = variant;
    }
    
    private String readSourceCode() throws IOException {
        Path path = Paths.get("src/main/java/testng/_17/" + variant + "/JUnitXMLReporter.java");
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
     * Check if m_configIssues field exists
     */
    public boolean hasConfigIssuesField() throws IOException {
        String source = readSourceCode();
        return source.contains("m_configIssues");
    }
    
    /**
     * Check if iteration over m_configIssues exists
     */
    public boolean hasIteration() throws IOException {
        String source = readSourceCode();
        return source.contains("for(ITestResult tr : m_configIssues)") || 
               source.contains("for (ITestResult tr : m_configIssues)") ||
               source.contains("for(ITestResult tr: m_configIssues)") ||
               source.contains("for(Object tr : m_configIssues)") ||
               source.contains("for (Object tr : m_configIssues)");
    }
    
    /**
     * Check if synchronized block is present for m_configIssues iteration.
     */
    public boolean hasSynchronizedBlock() throws IOException {
        String source = readSourceCode();
        return source.contains("synchronized (m_configIssues)") || 
               source.contains("synchronized(m_configIssues)");
    }
    
    /**
     * Check if the code is correctly fixed (synchronized block contains iteration).
     */
    public boolean isCorrectlyFixed() throws IOException {
        String source = readSourceCode();
        
        // Check for pattern: synchronized (m_configIssues) { ... for ... m_configIssues ... }
        int syncIndex = source.indexOf("synchronized (m_configIssues)");
        if (syncIndex == -1) {
            syncIndex = source.indexOf("synchronized(m_configIssues)");
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
        
        // Check if iteration over m_configIssues is inside the synchronized block
        String syncBlock = source.substring(braceStart, braceEnd);
        return syncBlock.contains("m_configIssues");
    }
}
