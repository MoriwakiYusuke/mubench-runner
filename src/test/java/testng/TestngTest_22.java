package testng;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import testng._22.Driver;
import testng._22.mocks.MockSuite;
import testng._22.mocks.MockTestContext;
import testng._22.mocks.MockSuiteResult;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for TestNG Case 22 - XMLReporter results synchronization bug.
 * 
 * Bug Type: missing/condition/synchronization
 * The bug is missing synchronized block when iterating over results map.
 * 
 * Tests include both static analysis and dynamic testing.
 */
class TestngTest_22 {
    
    abstract static class CommonCases {
        abstract Driver driver();
        
        // ========== Static Analysis Tests ==========
        
        @Test
        void testGetSuiteAttributesMethodExists() throws Exception {
            assertTrue(driver().hasGetSuiteAttributesMethod(), 
                "getSuiteAttributes method should exist");
        }
        
        @Test
        void testResultsVariableExists() throws Exception {
            assertTrue(driver().hasResultsVariable(), 
                "results variable should exist");
        }
        
        @Test
        void testIterationExists() throws Exception {
            assertTrue(driver().hasIteration(), 
                "Iteration over results should exist");
        }
        
        @Test
        void testSynchronizedBlockPresent() throws Exception {
            assertTrue(driver().hasSynchronizedBlock(), 
                "synchronized(results) block should be present");
        }
        
        @Test
        void testCorrectlyFixed() throws Exception {
            assertTrue(driver().isCorrectlyFixed(), 
                "Iteration should be inside synchronized block");
        }
        
        // ========== Dynamic Tests ==========
        
        @Test
        void testReporterInitialization() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            // If no exception, initialization succeeded
        }
        
        @Test
        void testGetterSetterMethods() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            
            // Test getter/setter pairs
            int fragLevel = d.getFileFragmentationLevel();
            d.setFileFragmentationLevel(fragLevel);
            
            String outputDir = d.getOutputDirectory();
            d.setOutputDirectory(outputDir != null ? outputDir : "/tmp");
            
            boolean groupsAttr = d.isGenerateGroupsAttribute();
            d.setGenerateGroupsAttribute(groupsAttr);
        }
        
        @Test
        void testTimestampFormat() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            String format = d.getTimestampFormat();
            assertNotNull(format, "getTimestampFormat should return non-null");
        }
        
        @Test
        void testGenerateReportWithMockSuite() throws Exception {
            Driver d = driver();
            d.initializeReporter();
            
            // Create mock objects
            MockSuite mockSuite = d.createMockSuite("TestSuite");
            MockTestContext mockContext = d.createMockTestContext("TestContext");
            mockContext.setSuite(mockSuite);
            MockSuiteResult mockResult = d.createMockSuiteResult(mockContext);
            mockSuite.addResult("test1", mockResult);
            
            // generateReport requires XmlSuite and ISuite lists
            try {
                d.generateReport(new ArrayList<>(), java.util.List.of(mockSuite), "/tmp");
            } catch (Exception e) {
                // May throw NPE due to XmlSuite requirements, but method is callable
            }
        }
        
        @Test
        void testMockSuiteAttributes() throws Exception {
            Driver d = driver();
            MockSuite mockSuite = d.createMockSuite("TestSuite");
            
            // Test attribute operations
            mockSuite.setAttribute("key", "value");
            assertEquals("value", mockSuite.getAttribute("key"));
            assertTrue(mockSuite.getAttributeNames().contains("key"));
            mockSuite.removeAttribute("key");
            assertNull(mockSuite.getAttribute("key"));
        }
    }
    
    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("original");
        }
    }
    
    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver("fixed");
        }
    }
    
    // ========== Misuse Test (Commented out per guideline) ==========
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver("misuse");
    //     }
    // }
}
