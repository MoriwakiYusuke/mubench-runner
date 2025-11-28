package cego._1;

import cego._1.mocks.Bitmap;
import cego._1.mocks.BitmapDrawable;
import cego._1.mocks.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver used by tests to trigger the activity logic.
 * Provides full access to all public methods.
 */
public class Driver {

    private final Object activity;
    private final Class<?> targetClass;

    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
            this.activity = targetClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to bootstrap driver for " + targetClassName, e);
        }
    }

    // === Direct method access ===

    /**
     * Set the current drawable on the activity
     */
    public void setCurrentDrawable(BitmapDrawable drawable) {
        invoke("setCurrentDrawable", new Class<?>[]{BitmapDrawable.class}, drawable);
    }

    /**
     * Open the current drawable in the standard image viewer
     */
    public void openCurrentDrawable() {
        invoke("openCurrentDrawable", new Class<?>[]{});
    }

    /**
     * Fetch the last started intent
     */
    public Intent fetchLastStartedIntent() {
        return (Intent) invoke("fetchLastStartedIntent", new Class<?>[]{});
    }

    // === Convenience method (backward compatible) ===

    /**
     * Combined operation: set drawable and open it, return the started intent
     */
    public Intent openBitmap(String bitmapLabel) {
        Bitmap bitmap = new Bitmap(bitmapLabel);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        setCurrentDrawable(drawable);
        openCurrentDrawable();
        return fetchLastStartedIntent();
    }

    // === Reflection helper ===

    private Object invoke(String methodName, Class<?>[] paramTypes, Object... args) {
        try {
            Method method = targetClass.getMethod(methodName, paramTypes);
            return method.invoke(activity, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
