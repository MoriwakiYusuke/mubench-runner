package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._101.Driver;
import ivantrendafilov_confucius._101.requirements.ConfigurationException;

import java.util.List;
import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース101のテスト
 * 
 * バグ: getShortList(String, String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_101 {

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid short list is parsed correctly")
        void testGetShortList_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "10,20,30");
            Driver driver = new Driver(getVariant(), props);
            
            List<Short> result = driver.getShortList("testKey", ",");
            
            assertEquals(3, result.size());
            assertEquals((short) 10, result.get(0));
            assertEquals((short) 20, result.get(1));
            assertEquals((short) 30, result.get(2));
        }

        @Test
        @DisplayName("Reproduction: Invalid short list throws appropriate exception")
        void testGetShortList_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "10,not_a_number,30");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getShortList("testKey", ",");
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
        void testGetShortList_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "10,bad,30");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getShortList("testKey", ",");
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
        void testGetShortList_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "10,bad,30");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getShortList("testKey", ",");
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
        @DisplayName("Fixed: Invalid value is skipped with warning log")
        void testGetShortList_SkipsInvalidValue() throws Exception {
            Properties props = createProperties("testKey", "10,bad,30");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは無効な値をスキップしてリストを返す
            List<Short> result = driver.getShortList("testKey", ",");
            assertEquals(2, result.size());
            assertEquals((short) 10, result.get(0));
            assertEquals((short) 30, result.get(1));
        }

        @Override
        @Test
        @DisplayName("Reproduction: Invalid short list skips invalid values (fixed behavior)")
        void testGetShortList_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "10,not_a_number,30");
            Driver driver = new Driver(getVariant(), props);
            
            // fixedでは例外をスローしない
            List<Short> result = driver.getShortList("testKey", ",");
            assertEquals(2, result.size());
        }
    }
}
