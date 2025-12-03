package rhino;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import rhino._1.Driver;

import static org.junit.jupiter.api.Assertions.*;

class RhinoTest_1 {

    abstract static class CommonCases {
        abstract Driver driver() throws Exception;

        @Test
        void testInitFunctionCalledOnce() throws Exception {
            Driver d = driver();
            assertTrue(d.hasCorrectInitFunctionPattern());
        }

        @Test
        void testNoDuplicateInitFunctionCall() throws Exception {
            Driver d = driver();
            assertFalse(d.hasDuplicateInitFunctionCall());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("original");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonCases {
//        @Override
//        Driver driver() throws Exception {
//            return new Driver("misuse");
//        }
//    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonCases {
        @Override
        Driver driver() throws Exception {
            return new Driver("fixed");
        }
    }
}
