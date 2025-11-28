package ivantrendafilov_confucius._97;

import ivantrendafilov_confucius._97.requirements.Configurable;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * Reflection-based driver for AbstractConfiguration variants.
 */
public class Driver {

    private static final String BASE_PACKAGE = "ivantrendafilov_confucius._97";
    private final Configurable configInstance;

    /**
     * @param variant "original", "misuse", "fixed" のいずれか
     * @param properties テスト用のプロパティ
     */
    public Driver(String variant, Properties properties) throws Exception {
        InputStream inputStream = propertiesToInputStream(properties);
        String className = BASE_PACKAGE + "." + variant + ".AbstractConfiguration";
        
        try {
            Class<?> clazz = Class.forName(className);
            Constructor<?> constructor = clazz.getConstructor(InputStream.class, String.class);
            this.configInstance = (Configurable) constructor.newInstance(inputStream, null);
        } catch (ClassNotFoundException e) {
            throw new IllegalArgumentException("Unknown variant: " + variant, e);
        }
    }

    private InputStream propertiesToInputStream(Properties properties) {
        StringBuilder sb = new StringBuilder();
        for (String key : properties.stringPropertyNames()) {
            sb.append(key).append("=").append(properties.getProperty(key)).append("\n");
        }
        return new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
    }

    // --- Public API Mapping ---

    public byte getByteValue(String key) {
        return configInstance.getByteValue(key);
    }

    public byte getByteValue(String key, byte defaultValue) {
        return configInstance.getByteValue(key, defaultValue);
    }

    public List<Byte> getByteList(String key, String separator) {
        return configInstance.getByteList(key, separator);
    }

    public List<Byte> getByteList(String key) {
        return configInstance.getByteList(key);
    }

    public long getLongValue(String key) {
        return configInstance.getLongValue(key);
    }

    public long getLongValue(String key, long defaultValue) {
        return configInstance.getLongValue(key, defaultValue);
    }

    public List<Long> getLongList(String key, String separator) {
        return configInstance.getLongList(key, separator);
    }

    public List<Long> getLongList(String key) {
        return configInstance.getLongList(key);
    }

    public short getShortValue(String key) {
        return configInstance.getShortValue(key);
    }

    public short getShortValue(String key, short defaultValue) {
        return configInstance.getShortValue(key, defaultValue);
    }

    public List<Short> getShortList(String key, String separator) {
        return configInstance.getShortList(key, separator);
    }

    public List<Short> getShortList(String key) {
        return configInstance.getShortList(key);
    }

    public String getStringValue(String key) {
        return configInstance.getStringValue(key);
    }

    public String getStringValue(String key, String defaultValue) {
        return configInstance.getStringValue(key, defaultValue);
    }

    public List<String> getStringList(String key, String separator) {
        return configInstance.getStringList(key, separator);
    }

    public List<String> getStringList(String key) {
        return configInstance.getStringList(key);
    }

    // === Boolean methods ===

    public boolean getBooleanValue(String key) {
        return configInstance.getBooleanValue(key);
    }

    public boolean getBooleanValue(String key, boolean defaultValue) {
        return configInstance.getBooleanValue(key, defaultValue);
    }

    public List<Boolean> getBooleanList(String key, String separator) {
        return configInstance.getBooleanList(key, separator);
    }

    public List<Boolean> getBooleanList(String key) {
        return configInstance.getBooleanList(key);
    }

    // === Char methods ===

    public char getCharValue(String key) {
        return configInstance.getCharValue(key);
    }

    public char getCharValue(String key, char defaultValue) {
        return configInstance.getCharValue(key, defaultValue);
    }

    public List<Character> getCharList(String key, String separator) {
        return configInstance.getCharList(key, separator);
    }

    public List<Character> getCharList(String key) {
        return configInstance.getCharList(key);
    }

    // === Double methods ===

    public double getDoubleValue(String key) {
        return configInstance.getDoubleValue(key);
    }

    public double getDoubleValue(String key, double defaultValue) {
        return configInstance.getDoubleValue(key, defaultValue);
    }

    public List<Double> getDoubleList(String key, String separator) {
        return configInstance.getDoubleList(key, separator);
    }

    public List<Double> getDoubleList(String key) {
        return configInstance.getDoubleList(key);
    }

    // === Float methods ===

    public float getFloatValue(String key) {
        return configInstance.getFloatValue(key);
    }

    public float getFloatValue(String key, float defaultValue) {
        return configInstance.getFloatValue(key, defaultValue);
    }

    public List<Float> getFloatList(String key, String separator) {
        return configInstance.getFloatList(key, separator);
    }

    public List<Float> getFloatList(String key) {
        return configInstance.getFloatList(key);
    }

    // === Int methods ===

    public int getIntValue(String key) {
        return configInstance.getIntValue(key);
    }

    public int getIntValue(String key, int defaultValue) {
        return configInstance.getIntValue(key, defaultValue);
    }

    public List<Integer> getIntList(String key, String separator) {
        return configInstance.getIntList(key, separator);
    }

    public List<Integer> getIntList(String key) {
        return configInstance.getIntList(key);
    }

    // === Properties/utility methods ===

    public java.util.Set<String> keySet() {
        return configInstance.keySet();
    }

    public Properties getProperties() {
        return configInstance.getProperties();
    }

    public <T> void setProperty(String key, T value) {
        configInstance.setProperty(key, value);
    }

    public <T> void setProperties(java.util.Map<String, T> properties) {
        configInstance.setProperties(properties);
    }

    public void setProperties(Properties properties) {
        configInstance.setProperties(properties);
    }

    public void clearProperty(String key) {
        configInstance.clearProperty(key);
    }

    public void reset() {
        configInstance.reset();
    }
}
