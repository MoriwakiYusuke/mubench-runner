package openaiab;

import openaiab._1.Driver;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OpenaiabTest_1 {

    private static final String BASE_PACKAGE = "openaiab._1";
    private static final String TARGET_CLASS = ".BillingActivity";

    abstract static class CommonCases {
        abstract Driver driver();
        abstract String variantName();

        @Test
        @DisplayName("onDestroy should call through to super")
        void onDestroyShouldCallSuper() {
            Driver driver = driver();
            driver.resetLifecycleFlags();
            driver.onDestroy();
            assertEquals(true, driver.wasUnityOnDestroyCalled(),
                    "Expected super.onDestroy() to be called for " + variantName());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".original" + TARGET_CLASS);
        }

        @Override
        String variantName() { return "original"; }

    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//        @Override
//        Driver driver() {
//            return new Driver(BASE_PACKAGE + ".misuse" + TARGET_CLASS);
//        }
//
//        @Override
//        String variantName() { return "misuse"; }
//
//    }


    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() {
            return new Driver(BASE_PACKAGE + ".fixed" + TARGET_CLASS);
        }

        @Override
        String variantName() { return "fixed"; }

    }
}
