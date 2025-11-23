package calligraphy._2;

import calligraphy._2.Driver.InvocationResult;
import calligraphy._2.mocks.CalligraphyConfig;
import calligraphy._2.mocks.Context;
import calligraphy._2.mocks.Paint;
import calligraphy._2.mocks.TextView;
import calligraphy._2.mocks.Typeface;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalligraphyTest_2 {

    abstract static class CommonCases {

        abstract Driver driver();

        abstract String expectedStyleFallback();

        abstract String expectedThemeFallback();

        @Test
        @DisplayName("applyFont(TextView, Typeface) sets typeface and paint flag")
        void applyFontWithTypeface() {
            Driver driver = driver();
            TextView textView = new TextView();
            Typeface typeface = new Typeface("fonts/Roboto-Regular.ttf");

            boolean applied = driver.applyFont(textView, typeface);

            assertTrue(applied);
            assertSame(typeface, textView.getTypeface());
            assertEquals(Paint.SUBPIXEL_TEXT_FLAG, textView.getPaintFlags());
        }

        @Test
        @DisplayName("applyFont(Context, TextView, CalligraphyConfig, String) honors explicit textView font")
        void applyFontHonorsExplicitFont() {
            Driver driver = driver();
            Context context = new Context();
            TextView textView = new TextView();
            CalligraphyConfig config = new CalligraphyConfig("config-font.ttf");

            driver.applyFont(context, textView, config, "view-font.ttf");

            assertNotNull(textView.getTypeface());
            assertEquals("view-font.ttf", textView.getTypeface().getPath());
            assertEquals(Paint.SUBPIXEL_TEXT_FLAG, textView.getPaintFlags());
        }

        @Test
        @DisplayName("applyFont(Context, TextView, CalligraphyConfig, String) falls back to config when override empty")
        void applyFontFallsBackToConfig() {
            Driver driver = driver();
            Context context = new Context();
            TextView textView = new TextView();
            CalligraphyConfig config = new CalligraphyConfig("config-font.ttf");

            driver.applyFont(context, textView, config, "");

            assertNotNull(textView.getTypeface());
            assertEquals("config-font.ttf", textView.getTypeface().getPath());
            assertEquals(Paint.SUBPIXEL_TEXT_FLAG, textView.getPaintFlags());
        }

        @Test
        @DisplayName("pullFontPathFromStyle gracefully handles getString() runtime failures")
        void pullFontPathFromStyleHandlesRuntime() {
            Driver driver = driver();

            InvocationResult result = driver.pullFontPathFromStyleWithFallback("style-fallback.ttf");

            assertTrue(result.isSuccess());
            assertEquals(expectedStyleFallback(), result.getValue());
        }

        @Test
        @DisplayName("pullFontPathFromTheme gracefully handles getString() runtime failures")
        void pullFontPathFromThemeHandlesRuntime() {
            Driver driver = driver();

            InvocationResult result = driver.pullFontPathFromThemeWithFallback("theme-fallback.ttf");

            assertTrue(result.isSuccess());
            assertEquals(expectedThemeFallback(), result.getValue());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {

        @Override
        Driver driver() {
            return new Driver("calligraphy._2.original.CalligraphyUtils");
        }

        @Override
        String expectedStyleFallback() {
            return null;
        }

        @Override
        String expectedThemeFallback() {
            return null;
        }

        @Test
        @DisplayName("pullFontPathFromTheme crashes when style is missing")
        void pullFontPathFromThemeMissingStyleFails() {
            InvocationResult result = driver().pullFontPathFromThemeWithMissingStyle();

            assertFalse(result.isSuccess());
            assertNotNull(result.getError());
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//
//        @Override
//        Driver driver() {
//            return new Driver("calligraphy._2.misuse.CalligraphyUtils");
//        }
//
//        @Override
//        String expectedStyleFallback() {
//            return null;
//        }
//
//        @Override
//        String expectedThemeFallback() {
//            return null;
//        }
//    }

    // @Nested
    // @DisplayName("Fixed")
    // class Fixed extends CommonCases {

    //     @Override
    //     Driver driver() {
    //         return new Driver("calligraphy._2.fixed.CalligraphyUtils");
    //     }

    //     @Override
    //     String expectedStyleFallback() {
    //         return "style-fallback.ttf";
    //     }

    //     @Override
    //     String expectedThemeFallback() {
    //         return "theme-fallback.ttf";
    //     }

    //     @Test
    //     @DisplayName("pullFontPathFromTheme returns null when style is missing")
    //     void pullFontPathFromThemeMissingStyleSucceeds() {
    //         InvocationResult result = driver().pullFontPathFromThemeWithMissingStyle();

    //         assertTrue(result.isSuccess());
    //         assertNull(result.getValue());
    //     }
    // }
}
