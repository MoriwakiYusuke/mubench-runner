package lnreadera._1;

import lnreadera._1.mocks.SherlockActivity;

import java.lang.reflect.Method;

/**
 * Reflection-based driver for DisplayImageActivity variants.
 * Tests whether onDestroy() calls super.onDestroy().
 */
public class Driver {

    private final SherlockActivity activity;
    private final Method onDestroy;

    public Driver(String targetClassName) {
        try {
            Class<?> clazz = Class.forName(targetClassName);
            this.activity = (SherlockActivity) clazz.getDeclaredConstructor().newInstance();
            this.onDestroy = clazz.getDeclaredMethod("onDestroy");
            this.onDestroy.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    /**
     * Invoke onDestroy() and check if super.onDestroy() was called.
     * @return true if super.onDestroy() was called, false otherwise
     */
    public boolean executeOnDestroyAndCheckSuperCalled() {
        try {
            onDestroy.invoke(activity);
            return activity.isSuperOnDestroyCalled();
        } catch (Exception e) {
            throw new RuntimeException("Failed to invoke onDestroy", e);
        }
    }
}
