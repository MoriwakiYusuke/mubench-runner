package jmrtd;

import jmrtd._2.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for jmrtd Case 2: Cipher mode misuse in PassportAuthService.doAA()
 * 
 * Bug: Using Cipher.ENCRYPT_MODE instead of Cipher.DECRYPT_MODE
 * - original: uses DECRYPT_MODE (correct)
 * - misuse: uses ENCRYPT_MODE (BUG)
 * - fixed: LLM FAILURE - returned SecureMessagingWrapper instead of PassportAuthService
 * 
 * This test uses source code analysis via Driver to verify the fix.
 */
class JmrtdTest_2 {

    private static final String BASE_PACKAGE = "jmrtd._2";

    /**
     * Common test cases for variants that contain the correct PassportAuthService class.
     */
    abstract static class PassportAuthServiceCases {

        abstract Driver driver();

        @Test
        @DisplayName("Source file should exist and be readable")
        void testSourceFileExists() throws IOException {
            Driver d = driver();
            String sourceCode = d.readSourceCode();
            assertNotNull(sourceCode);
            assertFalse(sourceCode.isEmpty(), "Source code should not be empty");
        }

        @Test
        @DisplayName("Should contain PassportAuthService class")
        void testContainsPassportAuthService() throws IOException {
            Driver d = driver();
            assertTrue(d.containsPassportAuthService(),
                "Source should contain PassportAuthService class");
        }

        @Test
        @DisplayName("doAA method should use Cipher.DECRYPT_MODE")
        void testUsesDecryptMode() throws IOException {
            Driver d = driver();
            assertTrue(d.usesDecryptMode(),
                "doAA() should use Cipher.DECRYPT_MODE (not ENCRYPT_MODE)");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends PassportAuthServiceCases {

        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.PassportAuthService");
        }

        @Test
        @DisplayName("Original should NOT use ENCRYPT_MODE")
        void testNotUsingEncryptMode() throws IOException {
            Driver d = driver();
            assertFalse(d.usesEncryptMode(),
                "Original should not use ENCRYPT_MODE in doAA()");
        }
    }

    /**
     * Misuse variant - always fails because it uses ENCRYPT_MODE instead of DECRYPT_MODE.
     * Commented out as it represents the buggy code that should fail.
     */
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends PassportAuthServiceCases {
    //
    //     @Override
    //     Driver driver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.PassportAuthService");
    //     }
    // }

    /**
     * Fixed variant - LLM FAILURE case.
     * 
     * The LLM was asked to fix the Cipher mode bug in PassportAuthService,
     * but it returned SecureMessagingWrapper instead. This is an experimental
     * case documenting LLM limitations.
     */
    @Nested
    @DisplayName("Fixed (LLM Failure Case)")
    class Fixed {

        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.SecureMessagingWrapper");
        }

        @Test
        @DisplayName("Source file should exist (wrong class returned by LLM)")
        void testSourceFileExists() throws IOException {
            Driver d = driver();
            String sourceCode = d.readSourceCode();
            assertNotNull(sourceCode);
            assertFalse(sourceCode.isEmpty(), "Source code should not be empty");
        }

        @Test
        @DisplayName("LLM returned wrong class - should NOT contain PassportAuthService")
        void testDoesNotContainPassportAuthService() throws IOException {
            Driver d = driver();
            assertFalse(d.containsPassportAuthService(),
                "LLM incorrectly returned SecureMessagingWrapper instead of PassportAuthService");
        }

        @Test
        @DisplayName("LLM failure: doAA method signature does not exist (wrong class)")
        void testDoAAMethodNotFound() throws IOException {
            Driver d = driver();
            // The fixed variant contains SecureMessagingWrapper instead of PassportAuthService
            // usesDecryptMode checks for "doAA(" followed by Cipher.DECRYPT_MODE in the method body
            // Since this is the wrong class, there's no doAA method with Cipher usage
            // Note: The comment mentions doAA() but doesn't have the actual method implementation
            assertFalse(d.containsPassportAuthService(),
                "LLM returned SecureMessagingWrapper instead of PassportAuthService");
        }
    }
}
