package asterisk_java._194;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;

/**
 * Reflection-based adapter for RtcpReceivedEvent variants.
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
            return targetClass.getConstructor(Object.class).newInstance("driver");
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException("Failed to construct target", e);
        }
    }

    // === From address methods ===
    public InetAddress getFromAddress(Object instance) throws Exception {
        return (InetAddress) invoke(instance, "getFromAddress");
    }

    public Integer getFromPort(Object instance) throws Exception {
        return (Integer) invoke(instance, "getFromPort");
    }

    public void setFrom(Object instance, String value) throws Exception {
        invoke(instance, "setFrom", value);
    }

    // === To address methods ===
    public InetAddress getToAddress(Object instance) throws Exception {
        return (InetAddress) invoke(instance, "getToAddress");
    }

    public Integer getToPort(Object instance) throws Exception {
        return (Integer) invoke(instance, "getToPort");
    }

    public void setTo(Object instance, String value) throws Exception {
        invoke(instance, "setTo", value);
    }

    // === Payload type methods ===
    public Long getPt(Object instance) throws Exception {
        return (Long) invoke(instance, "getPt");
    }

    public void setPt(Object instance, String value) throws Exception {
        invoke(instance, "setPt", value);
    }

    // === Reception reports methods ===
    public Long getReceptionReports(Object instance) throws Exception {
        return (Long) invoke(instance, "getReceptionReports");
    }

    public void setReceptionReports(Object instance, Long value) throws Exception {
        invoke(instance, "setReceptionReports", value);
    }

    // === Sender SSRC methods ===
    public Long getSenderSsrc(Object instance) throws Exception {
        return (Long) invoke(instance, "getSenderSsrc");
    }

    public void setSenderSsrc(Object instance, Long value) throws Exception {
        invoke(instance, "setSenderSsrc", value);
    }

    // === Packets lost methods ===
    public Long getPacketsLost(Object instance) throws Exception {
        return (Long) invoke(instance, "getPacketsLost");
    }

    public void setPacketsLost(Object instance, Long value) throws Exception {
        invoke(instance, "setPacketsLost", value);
    }

    // === Highest sequence methods ===
    public Long getHighestSequence(Object instance) throws Exception {
        return (Long) invoke(instance, "getHighestSequence");
    }

    public void setHighestSequence(Object instance, Long value) throws Exception {
        invoke(instance, "setHighestSequence", value);
    }

    // === Sequence number cycles methods ===
    public Long getSequenceNumberCycles(Object instance) throws Exception {
        return (Long) invoke(instance, "getSequenceNumberCycles");
    }

    public void setSequenceNumberCycles(Object instance, Long value) throws Exception {
        invoke(instance, "setSequenceNumberCycles", value);
    }

    // === Last SR methods ===
    public Double getLastSr(Object instance) throws Exception {
        return (Double) invoke(instance, "getLastSr");
    }

    public void setLastSr(Object instance, Double value) throws Exception {
        invoke(instance, "setLastSr", value);
    }

    // === RTT methods ===
    public Double getRtt(Object instance) throws Exception {
        return (Double) invoke(instance, "getRtt");
    }

    public void setRtt(Object instance, String value) throws Exception {
        invoke(instance, "setRtt", value);
    }

    public Long getRttAsMillseconds(Object instance) throws Exception {
        return (Long) invoke(instance, "getRttAsMillseconds");
    }

    // === Channel methods ===
    public String getChannel(Object instance) throws Exception {
        return (String) invoke(instance, "getChannel");
    }

    public void setChannel(Object instance, String value) throws Exception {
        invoke(instance, "setChannel", value);
    }

    // === Language methods ===
    public String getLanguage(Object instance) throws Exception {
        return (String) invoke(instance, "getLanguage");
    }

    public void setLanguage(Object instance, String value) throws Exception {
        invoke(instance, "setLanguage", value);
    }

    // === Report0 sequence number cycles methods ===
    public String getReport0SequenceNumberCycles(Object instance) throws Exception {
        return (String) invoke(instance, "getReport0SequenceNumberCycles");
    }

    public void setReport0SequenceNumberCycles(Object instance, String value) throws Exception {
        invoke(instance, "setReport0SequenceNumberCycles", value);
    }

    // === SSRC methods ===
    public String getSsrc(Object instance) throws Exception {
        return (String) invoke(instance, "getSsrc");
    }

    public void setSsrc(Object instance, String value) throws Exception {
        invoke(instance, "setSsrc", value);
    }

    // === Report0 LSR methods ===
    public String getReport0lsr(Object instance) throws Exception {
        return (String) invoke(instance, "getReport0lsr");
    }

    public void setReport0lsr(Object instance, String value) throws Exception {
        invoke(instance, "setReport0lsr", value);
    }

    // === Sent octets methods ===
    public Long getSentOctets(Object instance) throws Exception {
        return (Long) invoke(instance, "getSentOctets");
    }

    public void setSentOctets(Object instance, Long value) throws Exception {
        invoke(instance, "setSentOctets", value);
    }

    // === Report0 source SSRC methods ===
    public String getReport0Sourcessrc(Object instance) throws Exception {
        return (String) invoke(instance, "getReport0Sourcessrc");
    }

    public void setReport0Sourcessrc(Object instance, String value) throws Exception {
        invoke(instance, "setReport0Sourcessrc", value);
    }

    // === Report0 DLSR methods ===
    public Double getReport0dlsr(Object instance) throws Exception {
        return (Double) invoke(instance, "getReport0dlsr");
    }

    public void setReport0dlsr(Object instance, Double value) throws Exception {
        invoke(instance, "setReport0dlsr", value);
    }

    // === Uniqueid methods ===
    public String getUniqueid(Object instance) throws Exception {
        return (String) invoke(instance, "getUniqueid");
    }

    public void setUniqueid(Object instance, String value) throws Exception {
        invoke(instance, "setUniqueid", value);
    }

    // === Report0 cumulative lost methods ===
    public Integer getReport0CumulativeLost(Object instance) throws Exception {
        return (Integer) invoke(instance, "getReport0CumulativeLost");
    }

    public void setReport0CumulativeLost(Object instance, Integer value) throws Exception {
        invoke(instance, "setReport0CumulativeLost", value);
    }

    // === Report0 fraction lost methods ===
    public Integer getReport0FractionLost(Object instance) throws Exception {
        return (Integer) invoke(instance, "getReport0FractionLost");
    }

    public void setReport0FractionLost(Object instance, Integer value) throws Exception {
        invoke(instance, "setReport0FractionLost", value);
    }

    // === Report0 IA jitter methods ===
    public Integer getReport0iaJitter(Object instance) throws Exception {
        return (Integer) invoke(instance, "getReport0iaJitter");
    }

    public void setReport0iaJitter(Object instance, Integer value) throws Exception {
        invoke(instance, "setReport0iaJitter", value);
    }

    // === Sent NTP methods ===
    public String getSentntp(Object instance) throws Exception {
        return (String) invoke(instance, "getSentntp");
    }

    public void setSentntp(Object instance, String value) throws Exception {
        invoke(instance, "setSentntp", value);
    }

    // === Sent RTP methods ===
    public Long getSentrtp(Object instance) throws Exception {
        return (Long) invoke(instance, "getSentrtp");
    }

    public void setSentrtp(Object instance, Long value) throws Exception {
        invoke(instance, "setSentrtp", value);
    }

    // === Report count methods ===
    public Integer getReportCount(Object instance) throws Exception {
        return (Integer) invoke(instance, "getReportCount");
    }

    public void setReportCount(Object instance, Integer value) throws Exception {
        invoke(instance, "setReportCount", value);
    }

    // === Report0 highest sequence methods ===
    public Integer getReport0HighestSequence(Object instance) throws Exception {
        return (Integer) invoke(instance, "getReport0HighestSequence");
    }

    public void setReport0HighestSequence(Object instance, Integer value) throws Exception {
        invoke(instance, "setReport0HighestSequence", value);
    }

    // === LinkedId methods ===
    public String getLinkedId(Object instance) throws Exception {
        return (String) invoke(instance, "getLinkedId");
    }

    public void setLinkedId(Object instance, String value) throws Exception {
        invoke(instance, "setLinkedId", value);
    }

    // === Sent packets methods ===
    public Integer getSentPackets(Object instance) throws Exception {
        return (Integer) invoke(instance, "getSentPackets");
    }

    public void setSentPackets(Object instance, Integer value) throws Exception {
        invoke(instance, "setSentPackets", value);
    }

    // === Account code methods ===
    public String getAccountCode(Object instance) throws Exception {
        return (String) invoke(instance, "getAccountCode");
    }

    public void setAccountCode(Object instance, String value) throws Exception {
        invoke(instance, "setAccountCode", value);
    }

    // === Convenience methods (backward compatible) ===
    public Long parsePayloadType(String value) throws Exception {
        Object instance = newInstance();
        setPt(instance, value);
        return getPt(instance);
    }

    public Throwable captureException(String value) {
        Object instance = newInstance();
        try {
            setPt(instance, value);
            return null;
        } catch (Exception e) {
            return e;
        }
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
