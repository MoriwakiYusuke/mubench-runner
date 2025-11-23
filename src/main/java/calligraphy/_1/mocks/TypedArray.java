package calligraphy._1.mocks;

/**
 * Simplified TypedArray returning default values.
 */
public class TypedArray {

    public String getString(int index) {
        return null;
    }

    public CharSequence getText(int index) {
        return getString(index);
    }

    public int getIndexCount() {
        return 0;
    }

    public void recycle() {
        // no-op
    }
}
