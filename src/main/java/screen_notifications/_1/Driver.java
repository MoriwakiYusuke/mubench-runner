package screen_notifications._1;

import screen_notifications._1.mocks.android.content.pm.ApplicationInfo;
import screen_notifications._1.mocks.android.content.pm.PackageManager;
import screen_notifications._1.mocks.android.graphics.drawable.Drawable;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver exposing the public API of AppsActivity for tests.
 */
public class Driver {

    private final Class<?> targetClass;
    private Object activityInstance;
    private PackageManager packageManager;
    private Drawable defaultIcon;

    /**
     * Constructor with PackageManager only.
     * Maps to: AppsActivity(PackageManager pm)
     */
    public Driver(String targetClassName) {
        this(targetClassName, null);
    }

    /**
     * Constructor with PackageManager and default icon.
     * Maps to: AppsActivity(PackageManager pm, Drawable defaultIcon)
     */
    public Driver(String targetClassName, Drawable defaultIcon) {
        try {
            this.targetClass = Class.forName(targetClassName);
            this.packageManager = new PackageManager();
            this.defaultIcon = defaultIcon;
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to load target " + targetClassName, e);
        }
    }

    public PackageManager getPackageManager() {
        return packageManager;
    }

    public void addApp(String packageName, String label, boolean throwOOMOnLoadIcon) {
        ApplicationInfo appInfo = new ApplicationInfo(packageName, label);
        appInfo.setThrowOOMOnLoadIcon(throwOOMOnLoadIcon);
        packageManager.addInstalledApplication(appInfo);
    }

    /**
     * Maps to: Data loadInBackground(int id)
     */
    public Object loadInBackground(int id) {
        return invokeInstance("loadInBackground", new Class<?>[]{int.class}, id);
    }

    private Object invokeInstance(String name, Class<?>[] types, Object... args) {
        try {
            Method m = targetClass.getMethod(name, types);
            return m.invoke(getActivityInstance(), args);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private RuntimeException rethrow(Throwable t) {
        if (t instanceof RuntimeException runtime) {
            return runtime;
        }
        if (t instanceof Error error) {
            throw error;
        }
        return new RuntimeException(t);
    }

    private Object getActivityInstance() {
        if (activityInstance == null) {
            try {
                if (defaultIcon != null) {
                    // Use 2-arg constructor: AppsActivity(PackageManager, Drawable)
                    Constructor<?> ctor = targetClass.getConstructor(PackageManager.class, Drawable.class);
                    activityInstance = ctor.newInstance(packageManager, defaultIcon);
                } else {
                    // Use 1-arg constructor: AppsActivity(PackageManager)
                    Constructor<?> ctor = targetClass.getConstructor(PackageManager.class);
                    activityInstance = ctor.newInstance(packageManager);
                }
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return activityInstance;
    }
}
