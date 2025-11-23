package calligraphy._2.mocks;

/**
 * Simplified Typeface storing only the font path for traceability.
 */
public class Typeface {

    private final String path;

    public Typeface(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
