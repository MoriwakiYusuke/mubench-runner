package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._97.Driver;
import java.util.Properties;

/**
 * 動的テスト: getLongValue(String, long) の動作検証
 */
public class Ivantrendafilov_confuciusTest_97 {

    abstract static class CommonLogic {

        abstract Driver createDriver(Properties props) throws Exception;

        @Test
        @DisplayName("getLongValue with default should work correctly for valid long value")
        void testGetLongValueWithDefaultValidInput() throws Exception {
            Properties props = new Properties();
            props.setProperty("valid.key", "123456789");
            
            Driver driver = createDriver(props);
            
            long result = driver.getLongValue("valid.key", 0L);
            assertEquals(123456789L, result);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver(Properties props) throws Exception {
            return new Driver("original", props);
        }
    }

    /*
    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver createDriver(Properties props) throws Exception {
            return new Driver("misuse", props);
        }
    }
    */

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver(Properties props) throws Exception {
            return new Driver("fixed", props);
        }
    }
}
