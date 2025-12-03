package screen_notifications;

import screen_notifications._1.Driver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ScreenNotificationsTest_1 {

    private static final String BASE_PACKAGE = "screen_notifications._1";
    private static final String TARGET_CLASS = ".AppsActivity";

    abstract static class CommonCases {

        abstract Driver createDriver();
        abstract String variantName();

        @Test
        @DisplayName("loadInBackground should return Data with apps")
        void loadInBackgroundReturnsData() {
            Driver driver = createDriver();
            driver.addApp("com.test.app1", "Alpha App", false);
            driver.addApp("com.test.app2", "Beta App", false);

            Object data = driver.loadInBackground(0);
            assertNotNull(data, "Data should not be null for " + variantName());
        }

        @Test
        @DisplayName("loadInBackground should handle OutOfMemoryError gracefully")
        void loadInBackgroundHandlesOOM() {
            Driver driver = createDriver();
            driver.addApp("com.test.oom", "OOM App", true);  // This will throw OOM

            // Should not throw OutOfMemoryError
            assertDoesNotThrow(() -> driver.loadInBackground(0),
                    "Should handle OutOfMemoryError gracefully for " + variantName());
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
