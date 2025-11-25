package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._95.Driver;
import ivantrendafilov_confucius._95.requirements.ConfigurationException;

import java.util.List;
import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース95のテスト
 * 
 * バグ: getByteList(String, String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_95 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid byte list is parsed correctly")
        void testGetByteList_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "1,2,3");
            Driver driver = new Driver(getVariant(), props);
            
            List<Byte> result = driver.getByteList("testKey", ",");
            
            assertEquals(3, result.size());
            assertEquals((byte) 1, result.get(0));
            assertEquals((byte) 2, result.get(1));
            assertEquals((byte) 3, result.get(2));
        }

        @Test
        @DisplayName("Reproduction: Invalid byte list throws appropriate exception")
        void testGetByteList_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "1,not_a_number,3");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getByteList("testKey", ",");
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
        void testGetByteList_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "1,bad,3");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteList("testKey", ",");
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
        void testGetByteList_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "1,bad,3");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteList("testKey", ",");
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
        void testGetByteList_ThrowsConfigurationException() throws Exception {
            Properties props = createProperties("testKey", "1,bad,3");
            Driver driver = new Driver(getVariant(), props);
            
            ConfigurationException ex = assertThrows(ConfigurationException.class, () -> {
                driver.getByteList("testKey", ",");
            });
            assertTrue(ex.getMessage().contains("testKey"));
            assertNotNull(ex.getCause());
            assertInstanceOf(NumberFormatException.class, ex.getCause());
        }
    }
}
