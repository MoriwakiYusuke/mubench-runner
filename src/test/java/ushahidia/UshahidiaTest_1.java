package ushahidia;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ushahidia._1.Driver;
import ushahidia._1.mocks.Cursor;
import ushahidia._1.requirements.IOpenGeoSmsSchema;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for OpenGeoSmsDao variants.
 * Bug: Cursor.close() not called in getReportState() method.
 */
class UshahidiaTest_1 {

    abstract static class CommonLogic {
        abstract Driver createDriver() throws Exception;

        @Test
        @DisplayName("getReportState should close cursor when records exist")
        void testCursorClosedWhenRecordsExist() throws Exception {
            Driver driver = createDriver();
            driver.setQueryResult(1, IOpenGeoSmsSchema.STATE_PENDING);

            int result = driver.getReportState(123L);

            Cursor cursor = driver.getLastCursor();
            assertNotNull(cursor, "Cursor should have been created");
            assertTrue(cursor.isClosed(), "Cursor should be closed after getReportState()");
            assertEquals(IOpenGeoSmsSchema.STATE_PENDING, result);
        }

        @Test
        @DisplayName("getReportState should close cursor when no records found")
        void testCursorClosedWhenNoRecords() throws Exception {
            Driver driver = createDriver();
            driver.setQueryResult(0, 0);

            int result = driver.getReportState(456L);

            Cursor cursor = driver.getLastCursor();
            assertNotNull(cursor, "Cursor should have been created");
            assertTrue(cursor.isClosed(), "Cursor should be closed even when no records found");
            assertEquals(IOpenGeoSmsSchema.STATE_NOT_OPENGEOSMS, result);
        }

        @Test
        @DisplayName("addReport should return true on success")
        void testAddReport() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.addReport(100L));
        }

        @Test
        @DisplayName("setReportState should return true for valid states")
        void testSetReportState() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.setReportState(100L, IOpenGeoSmsSchema.STATE_PENDING));
            assertTrue(driver.setReportState(100L, IOpenGeoSmsSchema.STATE_SENT));
        }

        @Test
        @DisplayName("setReportState should return false for invalid state")
        void testSetReportStateInvalidState() throws Exception {
            Driver driver = createDriver();
            assertFalse(driver.setReportState(100L, 999));
        }

        @Test
        @DisplayName("deleteReport should return true on success")
        void testDeleteReport() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.deleteReport(100L));
        }

        @Test
        @DisplayName("deleteReports should return true")
        void testDeleteReports() throws Exception {
            Driver driver = createDriver();
            assertTrue(driver.deleteReports());
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("original");
        }
    }

    // Misuseはバグがあるため必ず失敗（Cursor.close()が呼ばれない）
    // @Nested
    // @DisplayName("Misuse")
    // class Misuse extends CommonLogic {
    //     @Override
    //     Driver createDriver() throws Exception {
    //         return new Driver("misuse");
    //     }
    // }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {
        @Override
        Driver createDriver() throws Exception {
            return new Driver("fixed");
        }
    }
}
