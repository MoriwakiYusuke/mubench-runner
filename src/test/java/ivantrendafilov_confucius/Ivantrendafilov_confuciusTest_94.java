package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._94.Driver;
import ivantrendafilov_confucius._94.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース94のテスト
 * 
 * バグ: getByteValue(String, byte)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_94 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid byte value is parsed correctly")
        void testGetByteValue_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "42");
            Driver driver = new Driver(getVariant(), props);
            
            byte result = driver.getByteValue("testKey", (byte) 0);
            
            assertEquals((byte) 42, result);
        }

        @Test
        @DisplayName("Normal: Default value is returned when key is missing")
        void testGetByteValue_DefaultValue() throws Exception {
            Properties props = new Properties();
            Driver driver = new Driver(getVariant(), props);
            
            byte result = driver.getByteValue("missingKey", (byte) 99);
            
            assertEquals((byte) 99, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid byte value throws appropriate exception")
        void testGetByteValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getByteValue("testKey", (byte) 0);
            });
        }
    }

    @Nested
    @DisplayName("Original")
    class OriginalTest extends CommonLogic {
        @Override
        String getVariant() { return "original"; }

        @Test
        @DisplayName("Original: Invalid value throws NumberFormatException with message")
        void testGetByteValue_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteValue("testKey", (byte) 0);
            });
            assertTrue(ex.getMessage().contains("testKey"));
        }
    }

    @Nested
    @DisplayName("Misuse")
    class MisuseTest extends CommonLogic {
        @Override
        String getVariant() { return "misuse"; }

        @Test
        @DisplayName("Misuse: Invalid value throws raw NumberFormatException (BUG)")
        void testGetByteValue_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteValue("testKey", (byte) 0);
            });
            assertFalse(ex.getMessage().contains("testKey"));
        }
    }

    @Nested
    @DisplayName("Fixed")
    class FixedTest extends CommonLogic {
        @Override
        String getVariant() { return "fixed"; }

        @Test
        @DisplayName("Fixed: Invalid value returns default value with warning log")
        void testGetByteValue_ReturnsDefaultOnInvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローせずに警告ログを出してデフォルト値を返す
            byte result = driver.getByteValue("testKey", (byte) 99);
            assertEquals((byte) 99, result);
        }

        @Override
        @Test
        @DisplayName("Reproduction: Invalid byte value returns default (fixed behavior)")
        void testGetByteValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローしない
            byte result = driver.getByteValue("testKey", (byte) 50);
            assertEquals((byte) 50, result);
        }
    }
}
