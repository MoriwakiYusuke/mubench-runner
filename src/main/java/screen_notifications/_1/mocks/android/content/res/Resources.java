package screen_notifications._1.mocks.android.content.res;

import screen_notifications._1.mocks.android.graphics.drawable.Drawable;

/**
 * Stub for Android's Resources class.
 */
public class Resources {
    public Drawable getDrawable(int id) {
        return new Drawable("default_icon_" + id);
    }
}
