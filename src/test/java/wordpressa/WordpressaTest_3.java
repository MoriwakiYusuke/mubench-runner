package wordpressa;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for wordpressa Case 3: EditPostContentFragment
 * 
 * Bug: In onFormatButtonClick(), mContentEditText.getText() may return null.
 * The code calls content.insert() without null check, causing NPE.
 * 
 * Fix: Add null check before using content:
 *   Editable content = mContentEditText.getText();
 *   if (content == null)
 *       return;
 */
public class WordpressaTest_3 {

    @Nested
    class CommonCases {
        
        /**
         * Test that the misuse code throws NPE when getText() returns null.
         */
        @Test
        void testMisuse_NullPointerException() {
            // The misuse code should throw NPE when getText() returns null
            boolean handledNullSafely = wordpressa._3.Driver.testOnFormatButtonClick(
                wordpressa._3.misuse.EditPostContentFragment.class);
            
            // Misuse should NOT handle null safely (should throw NPE)
            assertFalse(handledNullSafely, 
                "Misuse code should throw NullPointerException when getText() returns null");
        }
        
        /**
         * Test that the fixed code handles null safely.
         */
        @Test
        void testFixed_HandlesNullSafely() {
            // The fixed code should handle null safely
            boolean handledNullSafely = wordpressa._3.Driver.testOnFormatButtonClick(
                wordpressa._3.fixed.EditPostContentFragment.class);
            
            // Fixed should handle null safely (no NPE)
            assertTrue(handledNullSafely, 
                "Fixed code should handle null safely without throwing NullPointerException");
        }
        
        /**
         * Test that the original code behavior (same as misuse for this case).
         */
        @Test
        void testOriginal_NullPointerException() {
            // The original code should have the same bug as misuse
            boolean handledNullSafely = wordpressa._3.Driver.testOnFormatButtonClick(
                wordpressa._3.original.EditPostContentFragment.class);
            
            // Original should NOT handle null safely (should throw NPE)
            assertFalse(handledNullSafely, 
                "Original code should throw NullPointerException when getText() returns null");
        }
    }
}
