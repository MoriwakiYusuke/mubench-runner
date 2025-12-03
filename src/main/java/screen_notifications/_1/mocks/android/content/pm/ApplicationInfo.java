package screen_notifications._1.mocks.android.content.pm;

import screen_notifications._1.mocks.android.graphics.drawable.Drawable;

import java.util.Comparator;

/**
 * Stub for Android's ApplicationInfo class.
 */
public class ApplicationInfo {
    public String packageName;
    public CharSequence label;
    private Drawable icon;
    private boolean throwOOMOnLoadIcon = false;

    public ApplicationInfo() {
    }

    public ApplicationInfo(String packageName, String label) {
        this.packageName = packageName;
        this.label = label;
        this.icon = new Drawable();
    }

    public void setThrowOOMOnLoadIcon(boolean throwOOM) {
        this.throwOOMOnLoadIcon = throwOOM;
    }

    public CharSequence loadLabel(PackageManager pm) {
        return label;
    }

    public Drawable loadIcon(PackageManager pm) {
        if (throwOOMOnLoadIcon) {
            throw new OutOfMemoryError("Simulated OutOfMemoryError from loadIcon");
        }
        return icon;
    }

    public static class DisplayNameComparator implements Comparator<ApplicationInfo> {
        public DisplayNameComparator(PackageManager pm) {
        }

        @Override
        public int compare(ApplicationInfo o1, ApplicationInfo o2) {
            String s1 = o1.label != null ? o1.label.toString() : "";
            String s2 = o2.label != null ? o2.label.toString() : "";
            return s1.compareToIgnoreCase(s2);
        }
    }
}
