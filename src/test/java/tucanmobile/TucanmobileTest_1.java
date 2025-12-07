package tucanmobile;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import tucanmobile._1.Driver;
import tucanmobile._1.mocks.ProgressDialog;
import tucanmobile._1.requirements.AnswerObject;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for tucanmobile case 1: Dialog.dismiss() without checking isShowing().
 */
class TucanmobileTest_1 {

    private static final String BASE_PACKAGE = "tucanmobile._1";

    abstract static class CommonLogic {
        abstract Driver createDriver();

        @Test
        @DisplayName("Should not throw when dialog is showing and dismiss is called")
        void testDismissWhenDialogIsShowing() {
            Driver driver = createDriver();
            
            // onPreExecute creates the dialog
            driver.invokeOnPreExecute();
            
            ProgressDialog dialog = driver.getDialog();
            assertNotNull(dialog, "Dialog should be created in onPreExecute");
            assertTrue(dialog.isShowing(), "Dialog should be showing after onPreExecute");
            
            // onPostExecute should dismiss without error
            AnswerObject result = new AnswerObject("", "", null, null);
            assertDoesNotThrow(() -> driver.invokeOnPostExecute(result));
            
            assertFalse(dialog.isShowing(), "Dialog should not be showing after onPostExecute");
        }

        @Test
        @DisplayName("Should handle dialog not showing gracefully")
        void testDismissWhenDialogIsNotShowing() {
            Driver driver = createDriver();
            
            // onPreExecute creates the dialog
            driver.invokeOnPreExecute();
            
            ProgressDialog dialog = driver.getDialog();
            assertNotNull(dialog, "Dialog should be created in onPreExecute");
            
            // Simulate dialog already dismissed (not showing)
            dialog.setShowing(false);
            assertFalse(dialog.isShowing(), "Dialog should not be showing");
            
            // onPostExecute should not throw when dialog is not showing
            AnswerObject result = new AnswerObject("", "", null, null);
            assertDoesNotThrow(() -> driver.invokeOnPostExecute(result),
                    "Should not throw when dismissing a dialog that is not showing");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() {
            return new Driver(BASE_PACKAGE + ".original.SimpleSecureBrowser");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonLogic {
    //     @Override
    //     Driver createDriver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.SimpleSecureBrowser");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() {
            return new Driver(BASE_PACKAGE + ".fixed.SimpleSecureBrowser");
        }
    }
}
