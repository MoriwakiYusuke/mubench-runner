package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._95.Driver;
import java.util.List;
import java.util.Properties;

/**
 * 動的テスト: getByteList(String, String) の動作検証
 */
public class Ivantrendafilov_confuciusTest_95 {

    abstract static class CommonLogic {

        abstract Driver createDriver(Properties props) throws Exception;

        @Test
        @DisplayName("getByteList should work correctly for valid byte values")
        void testGetByteListValidInput() throws Exception {
            Properties props = new Properties();
            props.setProperty("valid.list", "1,2,3");
            
            Driver driver = createDriver(props);
            
            List<Byte> result = driver.getByteList("valid.list", ",");
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals((byte) 1, result.get(0));
            assertEquals((byte) 2, result.get(1));
            assertEquals((byte) 3, result.get(2));
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
