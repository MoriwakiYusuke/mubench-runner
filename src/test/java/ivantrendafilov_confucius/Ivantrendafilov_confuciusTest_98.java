package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._98.Driver;
import ivantrendafilov_confucius._98.requirements.ConfigurationException;

import java.util.List;
import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース98のテスト
 * 
 * バグ: getLongList(String, String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_98 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid long list is parsed correctly")
        void testGetLongList_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "100,200,300");
            Driver driver = new Driver(getVariant(), props);
            
            List<Long> result = driver.getLongList("testKey", ",");
            
            assertEquals(3, result.size());
            assertEquals(100L, result.get(0));
            assertEquals(200L, result.get(1));
            assertEquals(300L, result.get(2));
        }

        @Test
        @DisplayName("Reproduction: Invalid long list throws appropriate exception")
        void testGetLongList_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "100,not_a_number,300");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getLongList("testKey", ",");
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
        void testGetLongList_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "100,bad,300");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getLongList("testKey", ",");
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
        void testGetLongList_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "100,bad,300");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getLongList("testKey", ",");
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
        void testGetLongList_ThrowsConfigurationException() throws Exception {
            Properties props = createProperties("testKey", "100,bad,300");
            Driver driver = new Driver(getVariant(), props);
            
            ConfigurationException ex = assertThrows(ConfigurationException.class, () -> {
                driver.getLongList("testKey", ",");
            });
            assertTrue(ex.getMessage().contains("testKey"));
            assertNotNull(ex.getCause());
            assertInstanceOf(NumberFormatException.class, ex.getCause());
        }
    }
}
