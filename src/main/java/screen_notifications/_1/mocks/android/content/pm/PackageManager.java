package screen_notifications._1.mocks.android.content.pm;

import java.util.ArrayList;
import java.util.List;

/**
 * Stub for Android's PackageManager class.
 */
public class PackageManager {
    public static final int GET_META_DATA = 128;

    private List<ApplicationInfo> installedApps = new ArrayList<>();

    public void addInstalledApplication(ApplicationInfo appInfo) {
        installedApps.add(appInfo);
    }

    public List<ApplicationInfo> getInstalledApplications(int flags) {
        return new ArrayList<>(installedApps);
    }
}
