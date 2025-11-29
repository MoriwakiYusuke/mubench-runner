package onosendai._1;

import android.content.Context;
import android.content.Intent;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Reflection-based driver exposing the public API of AlarmReceiver for tests.
 */
public class Driver {

    private final Class<?> targetClass;
    private Object receiverInstance;

    public Driver(String targetClassName) {
        try {
            this.targetClass = Class.forName(targetClassName);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to load target " + targetClassName, e);
        }
    }

    public void onReceive(Context context, Intent intent) {
        invokeInstance("onReceive", new Class<?>[]{Context.class, Intent.class}, context, intent);
    }

    public void configureAlarms(Context context) {
        invokeStatic("configureAlarms", new Class<?>[]{Context.class}, context);
    }

    public String readSourceCode() throws IOException {
        Path sourcePath = Path.of("src/main/java", targetClass.getName().replace('.', '/') + ".java");
        return Files.readString(sourcePath);
    }

    private Object invokeInstance(String name, Class<?>[] types, Object... args) {
        try {
            Method m = targetClass.getMethod(name, types);
            return m.invoke(getReceiverInstance(), args);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private Object invokeStatic(String name, Class<?>[] types, Object... args) {
        try {
            Method m = targetClass.getMethod(name, types);
            return m.invoke(null, args);
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
        return new RuntimeException(t);
    }

    private Object getReceiverInstance() {
        if (receiverInstance == null) {
            try {
                receiverInstance = targetClass.getDeclaredConstructor().newInstance();
            } catch (ReflectiveOperationException e) {
                throw new RuntimeException(e);
            }
        }
        return receiverInstance;
    }
}
