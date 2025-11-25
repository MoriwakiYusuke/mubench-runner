package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._97.Driver;
import ivantrendafilov_confucius._97.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース97のテスト
 * 
 * バグ: getLongValue(String, long)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_97 {

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
            
            long result = driver.getLongValue("testKey", 0L);
            
            assertEquals(123456789L, result);
        }

        @Test
        @DisplayName("Normal: Default value is returned when key is missing")
        void testGetLongValue_DefaultValue() throws Exception {
            Properties props = new Properties();
            Driver driver = new Driver(getVariant(), props);
            
            long result = driver.getLongValue("missingKey", 999L);
            
            assertEquals(999L, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid long value throws appropriate exception")
        void testGetLongValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getLongValue("testKey", 0L);
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
                driver.getLongValue("testKey", 0L);
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
                driver.getLongValue("testKey", 0L);
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
        void testGetLongValue_ReturnsDefaultOnInvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローせずに警告ログを出してデフォルト値を返す
            long result = driver.getLongValue("testKey", 999L);
            assertEquals(999L, result);
        }

        @Override
        @Test
        @DisplayName("Reproduction: Invalid long value returns default (fixed behavior)")
        void testGetLongValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローしない
            long result = driver.getLongValue("testKey", 50L);
            assertEquals(50L, result);
        }
    }
}
