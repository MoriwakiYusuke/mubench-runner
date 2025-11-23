package asterisk_java._194;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection based adapter for the RtcpReceivedEvent variants.
 */
public class Driver {

    private final Class<?> targetClass;

    public Driver(String targetClassName) {
        this.targetClass = load(targetClassName);
    }

    private static Class<?> load(String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unable to load target class: " + name, e);
        }
    }

    private Object newInstance() {
        try {
            return targetClass.getConstructor(Object.class).newInstance("driver");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to construct target", e);
        }
    }

    public Long parsePayloadType(String value) throws Exception {
        Object instance = newInstance();
        invoke(instance, "setPt", value);
        return (Long) invoke(instance, "getPt");
    }

    public Throwable captureException(String value) {
        Object instance = newInstance();
        try {
            invoke(instance, "setPt", value);
            return null;
        } catch (Exception e) {
            return e;
        }
    }

    private Object invoke(Object instance, String method, Object... args) throws Exception {
        try {
            Method target = findMethod(method, parameterTypes(args));
            return target.invoke(instance, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getTargetException();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            throw new RuntimeException(cause);
        }
    }

    private Method findMethod(String name, Class<?>[] parameterTypes) throws NoSuchMethodException {
        Method method = targetClass.getMethod(name, parameterTypes);
        method.setAccessible(true);
        return method;
    }

    private static Class<?>[] parameterTypes(Object[] args) {
        if (args == null) {
            return new Class<?>[0];
        }
        Class<?>[] types = new Class<?>[args.length];
        for (int i = 0; i < args.length; i++) {
            Object arg = args[i];
            types[i] = arg == null ? Object.class : arg.getClass();
        }
        return types;
    }
}
