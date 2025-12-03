package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._99.Driver;
import java.util.Properties;

/**
 * 動的テスト: getShortValue(String) の動作検証
 */
public class Ivantrendafilov_confuciusTest_99 {

    abstract static class CommonLogic {

        abstract Driver createDriver(Properties props) throws Exception;

        @Test
        @DisplayName("getShortValue should work correctly for valid short value")
        void testGetShortValueValidInput() throws Exception {
            Properties props = new Properties();
            props.setProperty("valid.key", "1234");
            
            Driver driver = createDriver(props);
            
            short result = driver.getShortValue("valid.key");
            assertEquals((short) 1234, result);
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

    /*
    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver(Properties props) throws Exception {
            return new Driver("fixed", props);
        }
    }
    */
}
