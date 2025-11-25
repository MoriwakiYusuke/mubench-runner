package ivantrendafilov_confucius._96;

import ivantrendafilov_confucius._96.requirements.Configurable;
import ivantrendafilov_confucius._96.mocks.InjectableConfigurationOriginal;
import ivantrendafilov_confucius._96.mocks.InjectableConfigurationMisuse;
import ivantrendafilov_confucius._96.mocks.InjectableConfigurationFixed;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

/**
 * AbstractConfiguration (Original/Misuse/Fixed) のメソッドを
 * 呼び出すための汎用ドライバ。
 * 
 * 各バリアント（original, misuse, fixed）のAbstractConfigurationを
 * InputStreamベースのコンストラクタでインスタンス化する。
 */
public class Driver {

    private final Configurable configInstance;

    /**
     * @param variant "original", "misuse", "fixed" のいずれか
     * @param properties テスト用のプロパティ
     */
    public Driver(String variant, Properties properties) throws Exception {
        InputStream inputStream = propertiesToInputStream(properties);
        
        switch (variant) {
            case "original":
                this.configInstance = new InjectableConfigurationOriginal(inputStream, null);
                break;
            case "misuse":
                this.configInstance = new InjectableConfigurationMisuse(inputStream, null);
                break;
            case "fixed":
                this.configInstance = new InjectableConfigurationFixed(inputStream, null);
                break;
            default:
                throw new IllegalArgumentException("Unknown variant: " + variant);
        }
    }

    private InputStream propertiesToInputStream(Properties properties) throws Exception {
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

    // --- 追加メソッド (他のケース用) ---

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
}
