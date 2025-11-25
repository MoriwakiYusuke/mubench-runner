package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._100.Driver;
import ivantrendafilov_confucius._100.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース100のテスト
 * 
 * バグ: getShortValue(String, short)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_100 {

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
            
            short result = driver.getShortValue("testKey", (short) 0);
            
            assertEquals((short) 1234, result);
        }

        @Test
        @DisplayName("Normal: Default value is returned when key is missing")
        void testGetShortValue_DefaultValue() throws Exception {
            Properties props = new Properties();
            Driver driver = new Driver(getVariant(), props);
            
            short result = driver.getShortValue("missingKey", (short) 999);
            
            assertEquals((short) 999, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid short value throws appropriate exception")
        void testGetShortValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getShortValue("testKey", (short) 0);
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
                driver.getShortValue("testKey", (short) 0);
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
                driver.getShortValue("testKey", (short) 0);
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
        void testGetShortValue_ReturnsDefaultOnInvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローせずに警告ログを出してデフォルト値を返す
            short result = driver.getShortValue("testKey", (short) 999);
            assertEquals((short) 999, result);
        }

        @Override
        @Test
        @DisplayName("Reproduction: Invalid short value returns default (fixed behavior)")
        void testGetShortValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローしない
            short result = driver.getShortValue("testKey", (short) 50);
            assertEquals((short) 50, result);
        }
    }
}
