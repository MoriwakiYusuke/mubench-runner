package screen_notifications._1.original;

import screen_notifications._1.mocks.android.content.pm.ApplicationInfo;
import screen_notifications._1.mocks.android.content.pm.PackageManager;
import screen_notifications._1.mocks.android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Original (correct) variant: Handles OutOfMemoryError from loadIcon() with fallback drawable.
 * From the fix commit of screen-notifications project.
 */
public class AppsActivity {

    private PackageManager packageManager;
    private Drawable defaultIcon;

    public AppsActivity(PackageManager pm) {
        this.packageManager = pm;
        this.defaultIcon = new Drawable("default_app_icon");
    }

    public AppsActivity(PackageManager pm, Drawable defaultIcon) {
        this.packageManager = pm;
        this.defaultIcon = defaultIcon;
    }

    public Data loadInBackground(int id) {
        final PackageManager pm = packageManager;
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);
        Collections.sort(packages, new ApplicationInfo.DisplayNameComparator(pm));

        Data data = new Data();
        data.sections = new ArrayList<Section>();
        data.apps = new App[packages.size()];

        String lastSection = "";
        String currentSection;
        for (int i = 0; i < packages.size(); i++) {
            ApplicationInfo appInfo = packages.get(i);

            data.apps[i] = new App();
            data.apps[i].name = (String) appInfo.loadLabel(pm);
            data.apps[i].packageName = appInfo.packageName;

            try {
                data.apps[i].icon = appInfo.loadIcon(pm);
            } catch (OutOfMemoryError e) {
                data.apps[i].icon = defaultIcon;
            }

            if (data.apps[i].name != null && data.apps[i].name.length() > 0) {
                currentSection = data.apps[i].name.substring(0, 1).toUpperCase();
                if (!lastSection.equals(currentSection)) {
                    data.sections.add(new Section(i, currentSection));
                    lastSection = currentSection;
                }
            }
        }

        return data;
    }
}

class Data {
    ArrayList<Section> sections;
    App[] apps;
}

class App {
    String name;
    String packageName;
    Drawable icon;
}

class Section {
    int startingIndex;
    String section;

    public Section(int startingIndex, String section) {
        this.startingIndex = startingIndex;
        this.section = section;
    }

    public String toString() {
        return section;
    }
}
