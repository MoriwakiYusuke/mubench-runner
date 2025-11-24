package hoverruan_weiboclient4j;

import hoverruan_weiboclient4j._128.Driver;
import hoverruan_weiboclient4j._128.params.Cid;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.*;

class HoverruanWeiboclient4jTest_128 {

    private static final String INVALID_VALUE = "invalid";

    abstract static class CommonLogic {
        abstract Driver getDriver();

        abstract void assertInvalidInput(Executable executable);

        @Test
        @DisplayName("cid(String) parses decimal values")
        void cidStringParsesDecimal() {
            Driver driver = getDriver();
            Cid cid = driver.createCidFromString("12345");
            assertEquals(12345L, cid.getValue(), "cid(String) should parse valid numeric input");
            assertTrue(cid.isValid(), "Resulting Cid must be considered valid for positive values");
        }

        @Test
        @DisplayName("cid(long) preserves the provided value")
        void cidLongRoundTrip() {
            Driver driver = getDriver();
            Cid cid = driver.createCidFromLong(67890L);
            assertEquals(67890L, cid.getValue(), "cid(long) should simply wrap the provided value");
        }

        @Test
        @DisplayName("cid(String) reports invalid input appropriately")
        void cidStringInvalidInput() {
            Driver driver = getDriver();
            Executable executable = () -> driver.createCidFromString(INVALID_VALUE);
            assertInvalidInput(executable);
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(hoverruan_weiboclient4j._128.original.CoreParameters.class);
        }

        @Override
        void assertInvalidInput(Executable executable) {
            NumberFormatException exception = assertThrows(NumberFormatException.class, executable);
            assertEquals("Cid value [" + INVALID_VALUE + "] is not a parsable Long", exception.getMessage(),
                    "Original implementation should wrap the NumberFormatException with a custom message");
        }
    }

    @Nested
    @DisplayName("Misuse")
    class Misuse extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(hoverruan_weiboclient4j._128.misuse.CoreParameters.class);
        }

        @Override
        void assertInvalidInput(Executable executable) {
            NumberFormatException exception = assertThrows(NumberFormatException.class, executable);
            assertEquals("For input string: \"" + INVALID_VALUE + "\"", exception.getMessage(),
                    "Misuse should leak the raw parseLong error message");
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver getDriver() {
            return new Driver(hoverruan_weiboclient4j._128.fixed.CoreParameters.class);
        }

        @Override
        void assertInvalidInput(Executable executable) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, executable);
            assertEquals("Invalid Cid value: " + INVALID_VALUE, exception.getMessage(),
                    "Fixed implementation should convert to IllegalArgumentException with descriptive message");
            assertNotNull(exception.getCause(), "Fixed exception should retain the original cause");
            assertTrue(exception.getCause() instanceof NumberFormatException,
                    "Fixed implementation should keep NumberFormatException as the cause");
        }
    }
}
