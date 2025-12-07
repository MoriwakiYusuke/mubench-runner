package wordpressa;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import wordpressa._1.Driver;
import wordpressa._1.mocks.ListView;
import wordpressa._1.mocks.SimperiumUtils;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test for wordpressa case 1: NotificationsListFragment.restoreListScrollPosition()
 * 
 * Bug: Calls ListFragment.getListView() without checking that ListFragment.isAdded(),
 * which might lead to crashes if the view is not yet initialized.
 */
class WordpressaTest_1 {

    abstract static class CommonLogic {

        abstract Driver driver();

        @BeforeEach
        void setUp() {
            SimperiumUtils.reset();
        }

        @Test
        @DisplayName("should restore scroll position when fragment is added and position is valid")
        void restoresScrollPositionWhenAdded() {
            Driver d = driver();
            boolean selectionSet = d.testRestoreScrollPosition(true, 2, 5);
            
            assertTrue(selectionSet, "Selection should be set when fragment is added");
        }

        @Test
        @DisplayName("should not restore scroll position when position is INVALID_POSITION")
        void doesNotRestoreWhenInvalidPosition() {
            Driver d = driver();
            boolean selectionSet = d.testRestoreScrollPosition(true, ListView.INVALID_POSITION, 5);
            
            assertFalse(selectionSet, "Selection should not be set when position is INVALID_POSITION");
        }

        @Test
        @DisplayName("should not restore scroll position when position exceeds note count")
        void doesNotRestoreWhenPositionExceedsCount() {
            Driver d = driver();
            boolean selectionSet = d.testRestoreScrollPosition(true, 10, 5);
            
            assertFalse(selectionSet, "Selection should not be set when position exceeds count");
        }

        @Test
        @DisplayName("should check isAdded() before accessing ListView")
        void checksIsAddedBeforeGetListView() {
            Driver d = driver();
            boolean hasCheck = d.checksIsAddedBeforeGetListView();
            
            assertTrue(hasCheck, "Should check isAdded() before calling getListView()");
        }

        @Test
        @DisplayName("should NOT set selection when fragment is not added")
        void doesNotSetSelectionWhenNotAdded() {
            Driver d = driver();
            boolean selectionSet = d.testRestoreScrollPosition(false, 2, 5);
            
            assertFalse(selectionSet, "Selection should NOT be set when fragment is not added");
        }
    }

    @Nested
    @DisplayName("Original")
    class Original extends CommonLogic {

        @Override
        Driver driver() {
            return new Driver("original");
        }
    }

    @Nested
    @DisplayName("Fixed")
    class Fixed extends CommonLogic {

        @Override
        Driver driver() {
            return new Driver("fixed");
        }
    }

//    @Nested
//    @DisplayName("Misuse")
//    class Misuse extends CommonLogic {
//
//        @Override
//        Driver driver() {
//            return new Driver("misuse");
//        }
//    }
}
