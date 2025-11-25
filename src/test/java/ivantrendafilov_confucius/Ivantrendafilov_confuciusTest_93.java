package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._93.Driver;
import ivantrendafilov_confucius._93.requirements.ConfigurationException;

import java.util.Properties;

/**
 * ivantrendafilov-confucius ケース93のテスト
 * 
 * バグ: getByteValue(String)でNumberFormatExceptionをキャッチして
 * ConfigurationExceptionにラップしていない
 */
public class Ivantrendafilov_confuciusTest_93 {

    // =========================================================
    //  Helper Methods
    // =========================================================

    private static Properties createProperties(String key, String value) {
        Properties props = new Properties();
        props.setProperty(key, value);
        return props;
    }

    // =========================================================
    //  Test Logic
    // =========================================================
    
    abstract static class CommonLogic {

        abstract String getVariant();

        @Test
        @DisplayName("Normal: Valid byte value is parsed correctly")
        void testGetByteValue_ValidValue() throws Exception {
            Properties props = createProperties("testKey", "42");
            Driver driver = new Driver(getVariant(), props);
            
            byte result = driver.getByteValue("testKey");
            
            assertEquals((byte) 42, result);
        }

        @Test
        @DisplayName("Normal: Maximum byte value is parsed correctly")
        void testGetByteValue_MaxValue() throws Exception {
            Properties props = createProperties("testKey", "127");
            Driver driver = new Driver(getVariant(), props);
            
            byte result = driver.getByteValue("testKey");
            
            assertEquals(Byte.MAX_VALUE, result);
        }

        @Test
        @DisplayName("Normal: Minimum byte value is parsed correctly")
        void testGetByteValue_MinValue() throws Exception {
            Properties props = createProperties("testKey", "-128");
            Driver driver = new Driver(getVariant(), props);
            
            byte result = driver.getByteValue("testKey");
            
            assertEquals(Byte.MIN_VALUE, result);
        }

        @Test
        @DisplayName("Reproduction: Invalid byte value throws appropriate exception")
        void testGetByteValue_InvalidValue() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // original/fixedはConfigurationExceptionをスロー
            // misuseはNumberFormatExceptionをスロー（バグ）
            assertThrows(RuntimeException.class, () -> {
                driver.getByteValue("testKey");
            });
        }

        @Test
        @DisplayName("Reproduction: Out of range value throws appropriate exception")
        void testGetByteValue_OutOfRange() throws Exception {
            Properties props = createProperties("testKey", "999");
            Driver driver = new Driver(getVariant(), props);
            
            assertThrows(RuntimeException.class, () -> {
                driver.getByteValue("testKey");
            });
        }
    }

    // =========================================================
    //  Nested Test Classes
    // =========================================================

    @Nested
    @DisplayName("Original")
    class OriginalTest extends CommonLogic {
        @Override
        String getVariant() {
            return "original";
        }

        @Test
        @DisplayName("Original: Invalid value throws NumberFormatException with message")
        void testGetByteValue_ThrowsNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteValue("testKey");
            });
            assertTrue(ex.getMessage().contains("testKey"));
        }
    }

    @Nested
    @DisplayName("Misuse")
    class MisuseTest extends CommonLogic {
        @Override
        String getVariant() {
            return "misuse";
        }

        @Test
        @DisplayName("Misuse: Invalid value throws raw NumberFormatException (BUG)")
        void testGetByteValue_ThrowsRawNumberFormatException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            // misuseはNumberFormatExceptionをそのままスロー（バグ）
            // メッセージにキー名が含まれない
            NumberFormatException ex = assertThrows(NumberFormatException.class, () -> {
                driver.getByteValue("testKey");
            });
            // バグ: キー名が含まれないためユーザーはどのキーが問題かわからない
            assertFalse(ex.getMessage().contains("testKey"));
        }
    }

    @Nested
    @DisplayName("Fixed")
    class FixedTest extends CommonLogic {
        @Override
        String getVariant() {
            return "fixed";
        }

        @Test
        @DisplayName("Fixed: Invalid value throws ConfigurationException with cause")
        void testGetByteValue_ThrowsConfigurationException() throws Exception {
            Properties props = createProperties("testKey", "not_a_number");
            Driver driver = new Driver(getVariant(), props);
            
            ConfigurationException ex = assertThrows(ConfigurationException.class, () -> {
                driver.getByteValue("testKey");
            });
            assertTrue(ex.getMessage().contains("testKey"));
            assertNotNull(ex.getCause());
            assertInstanceOf(NumberFormatException.class, ex.getCause());
        }
    }
}
