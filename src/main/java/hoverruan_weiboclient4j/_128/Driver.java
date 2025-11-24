package hoverruan_weiboclient4j._128;

import hoverruan_weiboclient4j._128.params.Cid;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for CoreParameters variants.
 */
public class Driver {

    private final Class<?> targetClass;
    private final Method cidFromString;
    private final Method cidFromLong;

    public Driver(Class<?> targetClass) {
        try {
            this.targetClass = targetClass;
            this.cidFromString = targetClass.getMethod("cid", String.class);
            this.cidFromLong = targetClass.getMethod("cid", long.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalArgumentException("Failed to locate CoreParameters.cid overloads", e);
        }
    }

    public Cid createCidFromString(String value) {
        return invoke(cidFromString, value);
    }

    public Cid createCidFromLong(long value) {
        return invoke(cidFromLong, value);
    }

    @SuppressWarnings("unchecked")
    private Cid invoke(Method method, Object... args) {
        try {
            return (Cid) method.invoke(null, args);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to access method on " + targetClass.getName(), e);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw new RuntimeException(cause);
        }
    }
}
