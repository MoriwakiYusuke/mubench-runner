package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._96.Driver;
import ivantrendafilov_confucius._96.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース96のテスト
 * 
 * バグ: getLongValue(String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_96 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid long value is parsed correctly")
        void testGetLongValue_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "123456789");
            Driver driver = new Driver(getVariant(), props);
            
            long result = driver.getLongValue("testKey");
            
            assertEquals(123456789L, result);
        }

        @Test
        @DisplayName("Normal: Maximum long value is parsed correctly")
        void testGetLongValue_MaxValue() throws Exception {
            Properties props = createProperties("testKey", String.valueOf(Long.MAX_VALUE));
            Driver driver = new Driver(getVariant(), props);
            
            long result = driver.getLongValue("testKey");
            
            assertEquals(Long.MAX_VALUE, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid long value throws appropriate exception")
        void testGetLongValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getLongValue("testKey");
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
        void testGetLongValue_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getLongValue("testKey");
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
        void testGetLongValue_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getLongValue("testKey");
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
        void testGetLongValue_ThrowsConfigurationException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            ConfigurationException ex = assertThrows(ConfigurationException.class, () -> {
                driver.getLongValue("testKey");
            });
            assertTrue(ex.getMessage().contains("testKey"));
            assertNotNull(ex.getCause());
            assertInstanceOf(NumberFormatException.class, ex.getCause());
        }
    }
}
