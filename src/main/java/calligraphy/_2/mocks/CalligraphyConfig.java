package calligraphy._2.mocks;

/**
 * Simplified configuration holder used in tests.
 */
public class CalligraphyConfig {

    private final String fontPath;

    public CalligraphyConfig(String fontPath) {
        this.fontPath = fontPath;
    }

    public boolean isFontSet() {
        return fontPath != null && !fontPath.isEmpty();
    }

    public String getFontPath() {
        return fontPath;
    }
}
