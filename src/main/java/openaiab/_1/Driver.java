package openaiab._1;

import android.content.Intent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import openaiab._1.mocks.com.unity3d.player.UnityPlayerActivity;

/**
 * Reflection-based driver that exposes BillingActivity's public lifecycle hooks.
 */
public class Driver {

    private final Class<?> targetClass;
    private final Object targetInstance;

    public Driver(String className) {
        try {
            this.targetClass = Class.forName(className);
            this.targetInstance = this.targetClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to load target " + className, e);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        invoke("onActivityResult", new Class<?>[]{int.class, int.class, Intent.class}, requestCode, resultCode, intent);
    }

    public void onDestroy() {
        invoke("onDestroy", new Class<?>[0]);
    }

    public void resetLifecycleFlags() {
        if (targetInstance instanceof UnityPlayerActivity unity) {
            unity.resetUnityOnDestroyFlag();
        }
    }

    public boolean wasUnityOnDestroyCalled() {
        if (targetInstance instanceof UnityPlayerActivity unity) {
            return unity.wasUnityOnDestroyCalled();
        }
        return false;
    }

    private Object invoke(String name, Class<?>[] parameterTypes, Object... args) {
        try {
            Method method = targetClass.getDeclaredMethod(name, parameterTypes);
            method.setAccessible(true);
            return method.invoke(targetInstance, args);
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new IllegalStateException("Failed to invoke " + name, e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException("Invocation failed for " + name, e.getCause());
        }
    }
}
