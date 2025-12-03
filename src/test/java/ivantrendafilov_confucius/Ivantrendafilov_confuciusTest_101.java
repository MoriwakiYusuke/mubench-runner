package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._101.Driver;
import java.util.List;
import java.util.Properties;

/**
 * 動的テスト: getShortList(String, String) の動作検証
 */
public class Ivantrendafilov_confuciusTest_101 {

    abstract static class CommonLogic {

        abstract Driver createDriver(Properties props) throws Exception;

        @Test
        @DisplayName("getShortList should work correctly for valid short values")
        void testGetShortListValidInput() throws Exception {
            Properties props = new Properties();
            props.setProperty("valid.list", "10,20,30");
            
            Driver driver = createDriver(props);
            
            List<Short> result = driver.getShortList("valid.list", ",");
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals((short) 10, result.get(0));
            assertEquals((short) 20, result.get(1));
            assertEquals((short) 30, result.get(2));
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
