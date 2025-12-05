package screen_notifications._1;

import screen_notifications._1.mocks.android.content.pm.ApplicationInfo;
import screen_notifications._1.mocks.android.content.pm.PackageManager;
import screen_notifications._1.mocks.android.os.Bundle;
import screen_notifications._1.mocks.android.support.v4.content.Loader;
import screen_notifications._1.mocks.com.lukekorth.ez_loaders.EzLoaderInterface;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver exposing the public API of AppsActivity for tests.
 * 
 * AppsActivity extends FragmentActivity and implements EzLoaderInterface<Data>.
 * This driver provides access to all public methods.
 */
public class Driver {

    private final Class<?> targetClass;
    private Object activityInstance;

    /**
     * Constructor: Creates an instance of the target AppsActivity variant.
     */
    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
            // AppsActivity has a no-arg constructor (inherited from FragmentActivity)
            Constructor<?> ctor = targetClass.getConstructor();
            this.activityInstance = ctor.newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to load target " + targetClassName, e);
        }
    }

    /**
     * Get the PackageManager from the activity instance.
     */
    public PackageManager getPackageManager() {
        try {
            Method method = targetClass.getMethod("getPackageManager");
            return (PackageManager) method.invoke(activityInstance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Add an application to the PackageManager for testing.
     */
    public void addApp(String packageName, String label, boolean throwOOMOnLoadIcon) {
        ApplicationInfo appInfo = new ApplicationInfo(packageName, label);
        appInfo.setThrowOOMOnLoadIcon(throwOOMOnLoadIcon);
        getPackageManager().addInstalledApplication(appInfo);
    }

    // ===== FragmentActivity methods =====

    /**
     * Maps to: void onCreate(Bundle savedInstanceState)
     */
    public void onCreate(Bundle savedInstanceState) {
        invokeInstance("onCreate", new Class<?>[]{Bundle.class}, savedInstanceState);
    }

    /**
     * Maps to: void setContentView(int layoutResId)
     */
    public void setContentView(int layoutResId) {
        invokeInstance("setContentView", new Class<?>[]{int.class}, layoutResId);
    }

    // ===== EzLoaderInterface methods =====

    /**
     * Maps to: Loader<Data> onCreateLoader(int id, Bundle args)
     */
    public Object onCreateLoader(int id, Bundle args) {
        return invokeInstance("onCreateLoader", new Class<?>[]{int.class, Bundle.class}, id, args);
    }

    /**
     * Maps to: Data loadInBackground(int id)
     */
    public Object loadInBackground(int id) {
        return invokeInstance("loadInBackground", new Class<?>[]{int.class}, id);
    }

    /**
     * Maps to: void onLoadFinished(Loader<Data> loader, Data data)
     */
    public void onLoadFinished(Object loader, Object data) {
        invokeInstance("onLoadFinished", new Class<?>[]{Loader.class, getDataClass()}, loader, data);
    }

    /**
     * Maps to: void onLoaderReset(Loader<Data> loader)
     */
    public void onLoaderReset(Object loader) {
        invokeInstance("onLoaderReset", new Class<?>[]{Loader.class}, loader);
    }

    /**
     * Maps to: void onReleaseResources(Data data)
     */
    public void onReleaseResources(Object data) {
        invokeInstance("onReleaseResources", new Class<?>[]{getDataClass()}, data);
    }

    // ===== Menu methods =====

    /**
     * Maps to: boolean onCreateOptionsMenu(Menu menu)
     */
    public boolean onCreateOptionsMenu(Object menu) {
        try {
            Class<?> menuClass = Class.forName("screen_notifications._1.mocks.android.view.Menu");
            return (Boolean) invokeInstance("onCreateOptionsMenu", new Class<?>[]{menuClass}, menu);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Maps to: boolean onOptionsItemSelected(MenuItem item)
     */
    public boolean onOptionsItemSelected(Object item) {
        try {
            Class<?> menuItemClass = Class.forName("screen_notifications._1.mocks.android.view.MenuItem");
            return (Boolean) invokeInstance("onOptionsItemSelected", new Class<?>[]{menuItemClass}, item);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    // ===== Helper methods =====

    private Class<?> getDataClass() {
        try {
            String packageName = targetClass.getPackageName();
            return Class.forName(packageName + ".Data");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeInstance(String name, Class<?>[] types, Object... args) {
        try {
            Method m = targetClass.getMethod(name, types);
            return m.invoke(activityInstance, args);
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
}
