package onosendai;

import onosendai._1.Driver;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertTrue;

class OnosendaiTest_1 {

    private static final String BASE_PACKAGE = "onosendai._1";
    private static final String BASE_SOURCE = "src/main/java/onosendai/_1/";
    private static final String TARGET_CLASS = ".AlarmReceiver";

    abstract static class CommonCases {

        abstract String variant();

        Driver driver() {
            return new Driver(BASE_PACKAGE + "." + variant() + TARGET_CLASS);
        }

        @Test
        @DisplayName("BatteryHelper must use application context")
        void batteryHelperUsesApplicationContext() throws IOException {
            String source = Files.readString(Path.of(BASE_SOURCE + variant() + "/AlarmReceiver.java"));
            boolean usesAppContext = source.contains("BatteryHelper.level(context.getApplicationContext())")
                    || source.contains("BatteryHelper.level(appContext)");
            assertTrue(usesAppContext, "BatteryHelper must be called with application context");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        String variant() {
            return "original";
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//        @Override
//        String variant() {
//            return "misuse";
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        String variant() {
            return "fixed";
        }
    }
}
