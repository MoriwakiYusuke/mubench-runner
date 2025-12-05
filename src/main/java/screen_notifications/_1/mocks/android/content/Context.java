package screen_notifications._1.mocks.android.content;

import screen_notifications._1.mocks.android.content.pm.PackageManager;
import screen_notifications._1.mocks.android.content.res.Resources;

/**
 * Stub for Android's Context class.
 */
public class Context {
    protected PackageManager packageManager = new PackageManager();
    protected Resources resources = new Resources();

    public PackageManager getPackageManager() {
        return packageManager;
    }

    public Resources getResources() {
        return resources;
    }
}
