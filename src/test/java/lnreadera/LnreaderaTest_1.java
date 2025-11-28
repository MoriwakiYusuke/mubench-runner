package lnreadera;

import lnreadera._1.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LnreaderaTest_1 {

    private static final String BASE_PACKAGE = "lnreadera._1";

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
            return new Driver(BASE_PACKAGE + ".original.DisplayImageActivity");
        }
    }

    // Misuseは常にコメントアウト（バグがあるため必ず失敗）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonCases {
    //     @Override
    //     Driver driver() {
    //         return new Driver(BASE_PACKAGE + ".misuse.DisplayImageActivity");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed.DisplayImageActivity");
        }
    }
}
