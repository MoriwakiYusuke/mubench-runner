package calligraphy._2.mocks;

/**
 * Simplified TextView that stores typeface and paint flags.
 */
public class TextView {

    private Typeface typeface;
    private int paintFlags;

    public void setTypeface(Typeface typeface) {
        this.typeface = typeface;
    }

    public Typeface getTypeface() {
        return typeface;
    }

    public void setPaintFlags(int flags) {
        this.paintFlags = flags;
    }

    public int getPaintFlags() {
        return paintFlags;
    }
}
