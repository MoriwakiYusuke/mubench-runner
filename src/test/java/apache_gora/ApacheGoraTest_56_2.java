package apache_gora._56_2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Properties;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 * WritableUtils#writeProperties の flush 漏れによる欠落を再現するテスト。
 * Driver 経由で Original/Fixed/Misuse を切り替えて挙動を比較する。
 */
public class ApacheGoraTest_56_2 {

    private static Properties buildSampleProperties() {
        Properties props = new Properties();
        props.setProperty("keyBlah", "valueBlah");
        props.setProperty("keyBlah2", "valueBlah2");
        return props;
    }

    abstract static class CommonCase {
        abstract Driver driver();

        @Test
        @DisplayName("Properties remain after round trip")
        void writeThenReadKeepsAllEntries() throws Exception {
            Properties original = buildSampleProperties();
            Properties result = driver().writeThenRead(original);

            assertEquals(original, result);
            assertFalse(result.isEmpty(), "result should contain entries");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("apache_gora._56_2.original.TestWritableUtils");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCase {
//        @Override
//        Driver driver() {
//            return new Driver("apache_gora._56_2.misuse.TestWritableUtils");
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCase {
        @Override
        Driver driver() {
            return new Driver("apache_gora._56_2.fixed.TestWritableUtils");
        }
    }
}
