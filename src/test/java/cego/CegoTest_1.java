package cego._1;

import cego._1.mocks.Intent;
import cego._1.mocks.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CegoTest_1 {

    abstract static class CommonCases {

        abstract Driver driver();

        abstract String expectedMime();

        @BeforeEach
        void resetLog() {
            Log.reset();
        }

        @Test
        @DisplayName("viewImageInStandardApp uses the expected MIME type")
        void viewImageUsesExpectedMime() {
            Intent intent = driver().openBitmap("sample-bitmap");

            assertNotNull(intent);
            assertEquals(Intent.ACTION_VIEW, intent.getAction());
            assertNotNull(intent.getData());
            assertEquals(expectedMime(), intent.getType());
            assertNull(Log.getLastError());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("cego._1.original.cgeoimages");
        }

        @Override
        String expectedMime() {
            return "image/jpeg";
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//
//        @Override
//        Driver driver() {
//            return new Driver("cego._1.misuse.cgeoimages");
//        }
//
//        @Override
//        String expectedMime() {
//            return "image/jpeg";
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("cego._1.fixed.cgeoimages");
        }

        @Override
        String expectedMime() {
            return "image/jpeg";
        }
    }
}
