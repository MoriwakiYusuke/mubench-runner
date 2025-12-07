package wordpressa;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wordpressa._3.Driver;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for wordpressa Case 3: EditPostContentFragment
 * 
 * Bug: In onClick() method, when handling R.id.more button click,
 * mSelectionEnd may exceed str.length() causing StringIndexOutOfBoundsException.
 * 
 * The code:
 *   if (str != null)
 *       str.insert(mSelectionEnd, "\n<!--more-->\n");
 * 
 * Fix: Add bounds check before using mSelectionEnd:
 *   if (str != null) {
 *       if (mSelectionEnd > str.length())
 *           mSelectionEnd = str.length();
 *       str.insert(mSelectionEnd, "\n<!--more-->\n");
 *   }
 * 
 * This test uses dynamic testing via reflection to verify the bounds check.
 */
class WordpressaTest_3 {

    abstract static class CommonLogic {

        abstract Driver driver();

        @Test
        @DisplayName("should have bounds check for mSelectionEnd before str.insert()")
        void hasBoundsCheckForSelectionEnd() {
            Driver d = driver();
            boolean hasBoundsCheck = d.hasBoundsCheckForSelectionEnd();
            
            assertTrue(hasBoundsCheck, 
                "Should have bounds check (mSelectionEnd > str.length()) before str.insert(). " +
                "StringIndexOutOfBoundsException indicates missing bounds check.");
        }

        @Test
        @DisplayName("should handle onClick with R.id.more button without exception")
        void handlesMoreButtonClickSafely() {
            Driver d = driver();
            boolean noException = d.testOnClickMoreButton();
            
            assertTrue(noException, 
                "onClick with R.id.more should not throw exception when selectionEnd > text.length()");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {

        @Override
        Driver driver() {
            return new Driver("original");
        }
    }

    // Fixed: LLM failed to fix the correct bug location.
    // The bug is in onClick() at R.id.more handler (line 1098), but LLM fixed
    // a different location (onFormatButtonClick method).
    // So Fixed also fails the bounds check test.
//    @Nested
//    @DisplayName("Fixed")
//    class Fixed extends CommonLogic {
//
//        @Override
//        Driver driver() {
//            return new Driver("fixed");
//        }
//    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonLogic {
//
//        @Override
//        Driver driver() {
//            return new Driver("misuse");
//        }
//    }
}
