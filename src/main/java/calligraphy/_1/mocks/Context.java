package calligraphy._1.mocks;

/**
 * Simplified stand-in for android.content.Context.
 */
public class Context {

    private final Resources resources = new Resources();

    public AssetManager getAssets() {
        return new AssetManager();
    }

    public Resources getResources() {
        return resources;
    }

    public Resources.Theme getTheme() {
        return resources.getTheme();
    }

    public TypedArray obtainStyledAttributes(AttributeSet attrs, int[] attributeIds) {
        return new TypedArray();
    }

    public String getString(int resId) {
        return "res-" + resId;
    }
}
