package calligraphy._2.mocks;

/**
 * Simplified TextUtils implementation.
 */
public final class TextUtils {

    private TextUtils() {
    }

    public static boolean isEmpty(CharSequence sequence) {
        return sequence == null || sequence.length() == 0;
    }
}
