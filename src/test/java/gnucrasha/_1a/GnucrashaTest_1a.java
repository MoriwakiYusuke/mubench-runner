package gnucrasha._1a;

import gnucrasha._1a.mocks.GnuCashApplication;
import gnucrasha._1a.mocks.Intent;
import gnucrasha._1a.mocks.UxArgument;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GnucrashaTest_1a {

    abstract static class CommonCases {

        abstract Driver driver();

        @Test
        @DisplayName("starts PasscodeLockScreenActivity with account UID when session expired")
        void startsLockScreenWithUid() {
            Intent result = driver().executeOnResume(true, false, "ACTION_VIEW", "UID-123");

            assertNotNull(result);
            assertEquals("UID-123", result.getStringExtra(UxArgument.SELECTED_ACCOUNT_UID));
        }

        @Test
        @DisplayName("does not start lock screen when session is still active")
        void doesNotStartWhenSessionActive() {
            Intent result = driver().executeOnResume(true, true, "ACTION_VIEW", "UID-456");

            assertNull(result);
        }

        @Test
        @DisplayName("onPause records new session start timestamp")
        void onPauseUpdatesSessionTimestamp() {
            long delta = driver().executeOnPause();

            assertTrue(delta >= 0L);
            assertTrue(GnuCashApplication.PASSCODE_SESSION_INIT_TIME > 0L);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("gnucrasha._1a.original.PassLockActivity");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//
//        @Override
//        Driver driver() {
//            return new Driver("gnucrasha._1a.misuse.PassLockActivity");
//        }
//
//        @Test
//        @DisplayName("misuses long extra and loses account UID")
//        void losesAccountUid() {
//            Intent result = driver().executeOnResume(true, false, "ACTION_VIEW", "UID-123");
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
            return new Driver("gnucrasha._1a.fixed.PassLockActivity");
        }
    }
}
