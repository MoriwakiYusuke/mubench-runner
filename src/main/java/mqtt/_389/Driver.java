package mqtt._389;

import mqtt._389.requirements.MqttException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * Driver for MqttSubscribe variants.
 * Provides access to all public methods of MqttSubscribe via reflection.
 */
public class Driver {
    private static final String BASE_PACKAGE = "mqtt._389";

    private final Object instance;
    private final Class<?> targetClass;

    /**
     * Create a Driver for the specified variant.
     *
     * @param variant "original", "misuse", or "fixed"
     * @param names   topic names for subscription
     * @param qos     QoS levels for each topic
     */
    public Driver(String variant, String[] names, int[] qos) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".MqttSubscribe";
        this.targetClass = Class.forName(className);
        Constructor<?> ctor = targetClass.getConstructor(String[].class, int[].class);
        this.instance = ctor.newInstance(names, qos);
    }

    /**
     * Create a Driver for the specified variant (from wire data).
     *
     * @param variant "original", "misuse", or "fixed"
     * @param info    message info byte
     * @param data    wire data
     */
    public Driver(String variant, byte info, byte[] data) throws Exception {
        String className = BASE_PACKAGE + "." + variant + ".MqttSubscribe";
        this.targetClass = Class.forName(className);
        Constructor<?> ctor = targetClass.getConstructor(byte.class, byte[].class);
        this.instance = ctor.newInstance(info, data);
    }

    // ========== Public Methods ==========

    /**
     * Returns the payload bytes for this SUBSCRIBE message.
     * This is the method where the bug (missing flush) exists in misuse.
     */
    public byte[] getPayload() throws Exception {
        Method method = targetClass.getMethod("getPayload");
        try {
            return (byte[]) method.invoke(instance);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof MqttException) {
                throw (MqttException) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Returns the variable header bytes.
     */
    public byte[] getVariableHeader() throws Exception {
        Method method = targetClass.getDeclaredMethod("getVariableHeader");
        method.setAccessible(true);
        try {
            return (byte[]) method.invoke(instance);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof MqttException) {
                throw (MqttException) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Returns the message info byte.
     */
    public byte getMessageInfo() throws Exception {
        Method method = targetClass.getDeclaredMethod("getMessageInfo");
        method.setAccessible(true);
        return (byte) method.invoke(instance);
    }

    /**
     * Returns whether this message is retryable.
     */
    public boolean isRetryable() throws Exception {
        Method method = targetClass.getMethod("isRetryable");
        return (boolean) method.invoke(instance);
    }

    /**
     * Returns string representation of this message.
     */
    public String toStringValue() throws Exception {
        Method method = targetClass.getMethod("toString");
        return (String) method.invoke(instance);
    }

    // ========== Inherited Methods from MqttWireMessage ==========

    /**
     * Returns the message type.
     */
    public byte getType() throws Exception {
        Method method = targetClass.getMethod("getType");
        return (byte) method.invoke(instance);
    }

    /**
     * Returns the message ID.
     */
    public int getMessageId() throws Exception {
        Method method = targetClass.getMethod("getMessageId");
        return (int) method.invoke(instance);
    }

    /**
     * Sets the message ID.
     */
    public void setMessageId(int msgId) throws Exception {
        Method method = targetClass.getMethod("setMessageId", int.class);
        method.invoke(instance, msgId);
    }

    /**
     * Returns the key for this message.
     */
    public String getKey() throws Exception {
        Method method = targetClass.getMethod("getKey");
        return (String) method.invoke(instance);
    }

    /**
     * Returns the header bytes.
     */
    public byte[] getHeader() throws Exception {
        Method method = targetClass.getMethod("getHeader");
        try {
            return (byte[]) method.invoke(instance);
        } catch (java.lang.reflect.InvocationTargetException e) {
            if (e.getCause() instanceof MqttException) {
                throw (MqttException) e.getCause();
            }
            throw e;
        }
    }

    /**
     * Returns whether a message ID is required.
     */
    public boolean isMessageIdRequired() throws Exception {
        Method method = targetClass.getMethod("isMessageIdRequired");
        return (boolean) method.invoke(instance);
    }

    /**
     * Sets the duplicate flag.
     */
    public void setDuplicate(boolean duplicate) throws Exception {
        Method method = targetClass.getMethod("setDuplicate", boolean.class);
        method.invoke(instance, duplicate);
    }
}
