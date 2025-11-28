package asterisk_java._81;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Reflection-based adapter for AsyncAgiEvent variants.
 * Provides full method coverage for LLM analysis.
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

    public Object newInstance() {
        try {
            return targetClass.getConstructor(Object.class).newInstance("test-source");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to construct target", e);
        }
    }

    // === UniqueId methods ===
    public String getUniqueId(Object instance) throws Exception {
        return (String) invoke(instance, "getUniqueId");
    }

    public void setUniqueId(Object instance, String value) throws Exception {
        invoke(instance, "setUniqueId", value);
    }

    // === Channel methods ===
    public String getChannel(Object instance) throws Exception {
        return (String) invoke(instance, "getChannel");
    }

    public void setChannel(Object instance, String value) throws Exception {
        invoke(instance, "setChannel", value);
    }

    // === SubEvent methods ===
    public String getSubEvent(Object instance) throws Exception {
        return (String) invoke(instance, "getSubEvent");
    }

    public void setSubEvent(Object instance, String value) throws Exception {
        invoke(instance, "setSubEvent", value);
    }

    // === CommandId methods ===
    public String getCommandId(Object instance) throws Exception {
        return (String) invoke(instance, "getCommandId");
    }

    public void setCommandId(Object instance, String value) throws Exception {
        invoke(instance, "setCommandId", value);
    }

    // === Result methods ===
    public String getResult(Object instance) throws Exception {
        return (String) invoke(instance, "getResult");
    }

    public void setResult(Object instance, String value) throws Exception {
        invoke(instance, "setResult", value);
    }

    @SuppressWarnings("unchecked")
    public List<String> decodeResult(Object instance) throws Exception {
        return (List<String>) invoke(instance, "decodeResult");
    }

    // === Env methods ===
    public String getEnv(Object instance) throws Exception {
        return (String) invoke(instance, "getEnv");
    }

    public void setEnv(Object instance, String value) throws Exception {
        invoke(instance, "setEnv", value);
    }

    @SuppressWarnings("unchecked")
    public List<String> decodeEnv(Object instance) throws Exception {
        return (List<String>) invoke(instance, "decodeEnv");
    }

    // === Boolean check methods ===
    public boolean isStart(Object instance) throws Exception {
        return (Boolean) invoke(instance, "isStart");
    }

    public boolean isExec(Object instance) throws Exception {
        return (Boolean) invoke(instance, "isExec");
    }

    public boolean isEnd(Object instance) throws Exception {
        return (Boolean) invoke(instance, "isEnd");
    }

    // === Convenience methods (backward compatible) ===
    @SuppressWarnings("unchecked")
    public List<String> decodeEnv(String encoded) throws Exception {
        Object instance = newInstance();
        setEnv(instance, encoded);
        return decodeEnv(instance);
    }

    @SuppressWarnings("unchecked")
    public List<String> decodeResult(String encoded) throws Exception {
        Object instance = newInstance();
        setResult(instance, encoded);
        return decodeResult(instance);
    }

    // === Reflective invocation support ===
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
