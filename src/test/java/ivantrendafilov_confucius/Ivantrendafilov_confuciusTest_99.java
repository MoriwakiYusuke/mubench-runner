package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._99.Driver;
import ivantrendafilov_confucius._99.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース99のテスト
 * 
 * バグ: getShortValue(String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_99 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid short value is parsed correctly")
        void testGetShortValue_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "1234");
            Driver driver = new Driver(getVariant(), props);
            
            short result = driver.getShortValue("testKey");
            
            assertEquals((short) 1234, result);
        }

        @Test
        @DisplayName("Normal: Maximum short value is parsed correctly")
        void testGetShortValue_MaxValue() throws Exception {
            Properties props = createProperties("testKey", "32767");
            Driver driver = new Driver(getVariant(), props);
            
            short result = driver.getShortValue("testKey");
            
            assertEquals(Short.MAX_VALUE, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid short value throws appropriate exception")
        void testGetShortValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getShortValue("testKey");
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
        void testGetShortValue_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getShortValue("testKey");
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
        void testGetShortValue_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getShortValue("testKey");
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
        @DisplayName("Fixed: Invalid value throws ConfigurationException with cause")
        void testGetShortValue_ThrowsConfigurationException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            ConfigurationException ex = assertThrows(ConfigurationException.class, () -> {
                driver.getShortValue("testKey");
            });
            assertTrue(ex.getMessage().contains("testKey"));
            assertNotNull(ex.getCause());
            assertInstanceOf(NumberFormatException.class, ex.getCause());
        }
    }
}
