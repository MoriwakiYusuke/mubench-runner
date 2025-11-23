package calligraphy._2.mocks;

/**
 * Minimal Typeface loader used in tests.
 */
public final class TypefaceUtils {

    private TypefaceUtils() {
    }

    public static Typeface load(AssetManager assetManager, String path) {
        return new Typeface(path);
    }
}
