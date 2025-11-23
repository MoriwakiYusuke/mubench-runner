package cego._1;

import cego._1.mocks.Bitmap;
import cego._1.mocks.BitmapDrawable;
import cego._1.mocks.Intent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver used by tests to trigger the activity logic.
 */
public class Driver {

    private final Object activity;
    private final Method setCurrentDrawable;
    private final Method openCurrentDrawable;
    private final Method fetchLastIntent;

    public Driver(String targetClassName) {
        try {
            Class<?> clazz = Class.forName(targetClassName);
            this.activity = clazz.getDeclaredConstructor().newInstance();
            this.setCurrentDrawable = clazz.getMethod("setCurrentDrawable", BitmapDrawable.class);
            this.openCurrentDrawable = clazz.getMethod("openCurrentDrawable");
            this.fetchLastIntent = clazz.getMethod("fetchLastStartedIntent");
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to bootstrap driver for " + targetClassName, e);
        }
    }

    public Intent openBitmap(String bitmapLabel) {
        Bitmap bitmap = new Bitmap(bitmapLabel);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        try {
            setCurrentDrawable.invoke(activity, drawable);
            openCurrentDrawable.invoke(activity);
            return (Intent) fetchLastIntent.invoke(activity);
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
