package lnreadera;

import lnreadera._2.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class for lnreadera case 2: DisplayLightNovelContentActivity
 * Tests the fix for missing super.onDestroy() call
 */
class LnreaderaTest_2 {

    private static final String BASE_PACKAGE = "lnreadera._2";

    abstract static class CommonCases {
        abstract Driver driver();

        @Test
        @DisplayName("onDestroy() should call super.onDestroy()")
        void testOnDestroyCallsSuper() {
            Driver d = driver();
            boolean superCalled = d.executeOnDestroyAndCheckSuperCalled();
            assertTrue(superCalled, "super.onDestroy() should be called");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original.DisplayLightNovelContentActivity");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.DisplayLightNovelContentActivity");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.DisplayLightNovelContentActivity");
        }
    }
}
