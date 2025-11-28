package lnreadera._2;

import lnreadera._2.mocks.SherlockActivity;

import java.lang.reflect.Method;

/**
 * Reflection-based driver for DisplayLightNovelContentActivity variants.
 * Provides full access to all public methods.
 */
public class Driver {

    private final Class<?> targetClass;
    private final SherlockActivity activity;

    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
            this.activity = (SherlockActivity) targetClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    // === Activity instance access ===
    
    public SherlockActivity getActivity() {
        return activity;
    }

    // === Lifecycle methods ===

    /**
     * Invoke onCreate(Bundle)
     */
    public void onCreate(Object savedInstanceState) {
        invoke("onCreate", new Class<?>[]{getBundleClass()}, savedInstanceState);
    }

    /**
     * Invoke onDestroy() and check if super.onDestroy() was called.
     * @return true if super.onDestroy() was called, false otherwise
     */
    public boolean executeOnDestroyAndCheckSuperCalled() {
        invoke("onDestroy", new Class<?>[]{});
        return activity.isSuperOnDestroyCalled();
    }

    /**
     * Invoke onDestroy()
     */
    public void onDestroy() {
        invoke("onDestroy", new Class<?>[]{});
    }

    // === Menu methods ===

    /**
     * Invoke onCreateOptionsMenu(Menu)
     */
    public boolean onCreateOptionsMenu(Object menu) {
        return (Boolean) invoke("onCreateOptionsMenu", new Class<?>[]{getMenuClass()}, menu);
    }

    /**
     * Invoke onOptionsItemSelected(MenuItem)
     */
    public boolean onOptionsItemSelected(Object item) {
        return (Boolean) invoke("onOptionsItemSelected", new Class<?>[]{getMenuItemClass()}, item);
    }

    // === UI methods ===

    /**
     * Invoke toggleProgressBar(boolean)
     */
    public void toggleProgressBar(boolean show) {
        invoke("toggleProgressBar", new Class<?>[]{boolean.class}, show);
    }

    /**
     * Invoke setMessageDialog(ICallbackEventData)
     */
    public void setMessageDialog(Object message) {
        invoke("setMessageDialog", new Class<?>[]{getCallbackEventDataClass()}, message);
    }

    /**
     * Invoke getResult(AsyncTaskResult<?>)
     */
    public void getResult(Object result) {
        invoke("getResult", new Class<?>[]{getAsyncTaskResultClass()}, result);
    }

    /**
     * Invoke updateProgress(String, int, int, String)
     */
    public void updateProgress(String id, int current, int total, String message) {
        invoke("updateProgress", new Class<?>[]{String.class, int.class, int.class, String.class}, id, current, total, message);
    }

    /**
     * Invoke downloadListSetup(String, String, int)
     */
    public boolean downloadListSetup(String id, String toastText, int type) {
        return (Boolean) invoke("downloadListSetup", new Class<?>[]{String.class, String.class, int.class}, id, toastText, type);
    }

    // === Reflection helpers ===

    private Object invoke(String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = targetClass.getDeclaredMethod(methodName, paramTypes);
            method.setAccessible(true);
            return method.invoke(activity, args);
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke " + methodName, e);
        }
    }

    private Class<?> getBundleClass() {
        try {
            return Class.forName("lnreadera._2.mocks.Bundle");
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }

    private Class<?> getMenuClass() {
        try {
            return Class.forName("lnreadera._2.mocks.Menu");
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }

    private Class<?> getMenuItemClass() {
        try {
            return Class.forName("lnreadera._2.mocks.MenuItem");
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }

    private Class<?> getCallbackEventDataClass() {
        try {
            return Class.forName("lnreadera._2.mocks.ICallbackEventData");
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }

    private Class<?> getAsyncTaskResultClass() {
        try {
            return Class.forName("lnreadera._2.mocks.AsyncTaskResult");
        } catch (ClassNotFoundException e) {
            return Object.class;
        }
    }
}
