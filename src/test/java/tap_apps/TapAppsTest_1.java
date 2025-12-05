package tap_apps;

import org.junit.jupiter.api.*;
import tap_apps._1.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for tap-apps case 1: Cipher.getInstance() usage.
 * 
 * Bug: Cipher.getInstance("Blowfish") returns unsafe default configuration.
 * Fix: Cipher.getInstance("Blowfish/ECB/NoPadding") with explicit mode and padding.
 * 
 * Note: misuse.java has the bug, original.java is the fixed version.
 */
class TapAppsTest_1 {
    
    private static final String BASE_PACKAGE = "tap_apps._1";
    private static final String TARGET_CLASS = ".NSMobileMessenger";
    
    // Blowfish requires 8-byte key minimum
    private static final byte[] TEST_KEY = "12345678".getBytes();
    private static final byte[] TEST_DATA = "Test message data".getBytes();
    
    abstract static class CommonCases {
        
        abstract Driver createDriver();
        abstract String variantName();
        
        @Test
        @DisplayName("Driver should be created successfully")
        void testDriverCreation() {
            Driver driver = createDriver();
            assertNotNull(driver, "Driver should be created for " + variantName());
        }
        
        @Test
        @DisplayName("getPriority should return 0")
        void testGetPriority() throws Exception {
            Driver driver = createDriver();
            assertEquals((byte) 0, driver.getPriority(),
                "getPriority should return 0 for " + variantName());
        }
        
        @Test
        @DisplayName("padArray8 should pad to 8-byte boundary")
        void testPadArray8() throws Exception {
            Driver driver = createDriver();
            byte[] input = new byte[5];
            byte[] padded = driver.padArray8(input);
            assertEquals(8, padded.length, 
                "padArray8 should pad 5 bytes to 8 for " + variantName());
            
            byte[] input16 = new byte[16];
            byte[] padded16 = driver.padArray8(input16);
            assertEquals(16, padded16.length, 
                "padArray8 should not pad 16 bytes for " + variantName());
        }
        
        @Test
        @DisplayName("testProperty should validate strings correctly")
        void testTestProperty() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.testProperty("valid"),
                "testProperty should return true for valid string for " + variantName());
            assertFalse(driver.testProperty(null),
                "testProperty should return false for null for " + variantName());
            assertFalse(driver.testProperty(""),
                "testProperty should return false for empty string for " + variantName());
        }
        
        @Test
        @DisplayName("Cipher transformation should use explicit mode and padding")
        void testCipherHasExplicitModeAndPadding() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.hasExplicitModeAndPadding(),
                "Cipher.getInstance() should use explicit Algorithm/Mode/Padding format for " + variantName());
        }
        
        @Test
        @DisplayName("Should not have unsafe Cipher calls with just algorithm name")
        void testNoUnsafeCipherCalls() throws Exception {
            Driver driver = createDriver();
            assertFalse(driver.hasUnsafeCipherCall(),
                "Should not use Cipher.getInstance() with just algorithm name for " + variantName());
        }
        
        @Test
        @DisplayName("Round-trip encryption/decryption should work")
        void testRoundTrip() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.roundTripTest(TEST_KEY, TEST_DATA),
                "Round-trip encryption/decryption should preserve data for " + variantName());
        }
        
        @Test
        @DisplayName("Encryption should produce different output from input")
        void testEncryptionProducesDifferentOutput() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.encryptionProducesDifferentOutput(TEST_KEY, TEST_DATA),
                "Encryption should produce different output for " + variantName());
        }
    }
    
    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver createDriver() {
            return new Driver(BASE_PACKAGE + ".original" + TARGET_CLASS);
        }

        @Override
        String variantName() {
            return "original";
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//        @Override
//        Driver createDriver() {
//            return new Driver(BASE_PACKAGE + ".misuse" + TARGET_CLASS);
//        }
//
//        @Override
//        String variantName() {
//            return "misuse";
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver createDriver() {
            return new Driver(BASE_PACKAGE + ".fixed" + TARGET_CLASS);
        }

        @Override
        String variantName() {
            return "fixed";
        }
    }
}
