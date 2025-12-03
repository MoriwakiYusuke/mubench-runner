package ivantrendafilov_confucius;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

import ivantrendafilov_confucius._98.Driver;
import java.util.List;
import java.util.Properties;

/**
 * 動的テスト: getLongList(String, String) の動作検証
 */
public class Ivantrendafilov_confuciusTest_98 {

    abstract static class CommonLogic {

        abstract Driver createDriver(Properties props) throws Exception;

        @Test
        @DisplayName("getLongList should work correctly for valid long values")
        void testGetLongListValidInput() throws Exception {
            Properties props = new Properties();
            props.setProperty("valid.list", "100,200,300");
            
            Driver driver = createDriver(props);
            
            List<Long> result = driver.getLongList("valid.list", ",");
            assertNotNull(result);
            assertEquals(3, result.size());
            assertEquals(100L, result.get(0));
            assertEquals(200L, result.get(1));
            assertEquals(300L, result.get(2));
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
