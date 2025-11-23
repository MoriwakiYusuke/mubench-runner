package gnucrasha._1b;

import gnucrasha._1b.mocks.GnuCashApplication;
import gnucrasha._1b.mocks.Intent;
import gnucrasha._1b.mocks.UxArgument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GnucrashaTest_1b {

    abstract static class CommonCases {

        abstract Driver driver();

        @Test
        @DisplayName("successful passcode entry returns to caller with UID")
        void successfulPasscodeReturnsToCaller() {
            Intent result = driver().submitPasscode("1234", "1234", "caller.Clazz", "ACTION", "UID-789");

            assertNotNull(result);
            assertEquals("caller.Clazz", result.getClassName());
            assertEquals("UID-789", result.getStringExtra(UxArgument.SELECTED_ACCOUNT_UID));
            assertTrue(GnuCashApplication.PASSCODE_SESSION_INIT_TIME > 0L);
        }

        @Test
        @DisplayName("back press navigates home")
        void backPressNavigatesHome() {
            Intent home = driver().pressBack();

            assertNotNull(home);
            assertEquals(Intent.ACTION_MAIN, home.getAction());
            assertTrue(home.getCategories().contains(Intent.CATEGORY_HOME));
            assertEquals(Intent.FLAG_ACTIVITY_NEW_TASK, home.getFlags());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("gnucrasha._1b.original.PasscodeLockScreenActivity");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//
//        @Override
//        Driver driver() {
//            return new Driver("gnucrasha._1b.misuse.PasscodeLockScreenActivity");
//        }
//
//        @Test
//        @DisplayName("misuses long extra and loses account UID")
//        void losesAccountUid() {
//            Intent result = driver().submitPasscode("1234", "1234", "caller.Clazz", "ACTION", "UID-789");
//
//            assertNotNull(result);
//            assertNull(result.getStringExtra(UxArgument.SELECTED_ACCOUNT_UID));
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("gnucrasha._1b.fixed.PasscodeLockScreenActivity");
        }
    }
}
