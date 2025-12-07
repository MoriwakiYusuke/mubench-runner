package wordpressa._1;

import wordpressa._1.mocks.Bundle;
import wordpressa._1.mocks.ListView;
import wordpressa._1.mocks.NotesAdapter;
import wordpressa._1.mocks.SimperiumUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for NotificationsListFragment variants.
 * Tests the isAdded() check in restoreListScrollPosition() method.
 */
public class Driver {

    private static final String BASE_PACKAGE = "wordpressa._1";

    private final Object fragment;
    private final Class<?> fragmentClass;
    private final Method restoreListScrollPosition;
    private final Method refreshNotes;

    public Driver(String variant) {
        try {
            String className = BASE_PACKAGE + "." + variant + ".NotificationsListFragment";
            this.fragmentClass = Class.forName(className);
            this.fragment = fragmentClass.getDeclaredConstructor().newInstance();

            this.restoreListScrollPosition = fragmentClass.getDeclaredMethod("restoreListScrollPosition");
            this.restoreListScrollPosition.setAccessible(true);

            this.refreshNotes = fragmentClass.getDeclaredMethod("refreshNotes");
            this.refreshNotes.setAccessible(true);

            // Initialize fragment for testing
            initializeFragment();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for variant: " + variant, e);
        }
    }

    private void initializeFragment() throws ReflectiveOperationException {
        // Initialize mNotesAdapter
        Field adapterField = fragmentClass.getDeclaredField("mNotesAdapter");
        adapterField.setAccessible(true);
        NotesAdapter adapter = new NotesAdapter(null, SimperiumUtils.getNotesBucket());
        adapterField.set(fragment, adapter);
    }

    /**
     * Set the isAdded state of the fragment.
     */
    public void setIsAdded(boolean isAdded) {
        try {
            // Access the parent ListFragment's setIsAdded method
            Method setIsAddedMethod = fragment.getClass().getSuperclass().getMethod("setIsAdded", boolean.class);
            setIsAddedMethod.invoke(fragment, isAdded);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to set isAdded", e);
        }
    }

    /**
     * Set the restored list position.
     */
    public void setRestoredListPosition(int position) {
        try {
            Field field = fragmentClass.getDeclaredField("mRestoredListPosition");
            field.setAccessible(true);
            field.set(fragment, position);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to set mRestoredListPosition", e);
        }
    }

    /**
     * Get the restored list position.
     */
    public int getRestoredListPosition() {
        try {
            Field field = fragmentClass.getDeclaredField("mRestoredListPosition");
            field.setAccessible(true);
            return (int) field.get(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to get mRestoredListPosition", e);
        }
    }

    /**
     * Get the ListView from the fragment.
     */
    public ListView getListView() {
        try {
            Method getListViewMethod = fragment.getClass().getSuperclass().getMethod("getListView");
            return (ListView) getListViewMethod.invoke(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to get ListView", e);
        }
    }

    /**
     * Get the NotesAdapter.
     */
    public NotesAdapter getNotesAdapter() {
        try {
            Field field = fragmentClass.getDeclaredField("mNotesAdapter");
            field.setAccessible(true);
            return (NotesAdapter) field.get(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to get NotesAdapter", e);
        }
    }

    /**
     * Call restoreListScrollPosition method.
     * This is the method that contains the bug.
     */
    public void callRestoreListScrollPosition() {
        try {
            restoreListScrollPosition.invoke(fragment);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Test the bug scenario: When isAdded() is false and restoreListScrollPosition is called,
     * the misuse version will still attempt to access getListView() and set selection,
     * while the correct version (original/fixed) will check isAdded() first.
     *
     * @param isAdded whether the fragment is added to activity
     * @param restoredPosition the position to restore
     * @param noteCount number of notes in the adapter
     * @return true if setSelectionFromTop was called, false otherwise
     */
    public boolean testRestoreScrollPosition(boolean isAdded, int restoredPosition, int noteCount) {
        setIsAdded(isAdded);
        setRestoredListPosition(restoredPosition);
        
        // Set the note count
        getNotesAdapter().setCount(noteCount);
        
        // Reset the listView's selection tracking
        ListView listView = getListView();
        listView.setSelectionFromTop(ListView.INVALID_POSITION, 0);
        
        // Call the method
        callRestoreListScrollPosition();
        
        // Check if selection was set
        return listView.getSelectionFromTop() != ListView.INVALID_POSITION;
    }

    /**
     * Check if isAdded() check is present before getListView() call.
     * For the misuse version, this will be false.
     */
    public boolean checksIsAddedBeforeGetListView() {
        // Set isAdded to false and check if the method still tries to set selection
        setIsAdded(false);
        setRestoredListPosition(0);
        getNotesAdapter().setCount(5);
        
        ListView listView = getListView();
        listView.setSelectionFromTop(ListView.INVALID_POSITION, 0);
        
        callRestoreListScrollPosition();
        
        // If isAdded check is present, selection should NOT be set
        // If isAdded check is missing (misuse), selection WILL be set
        return listView.getSelectionFromTop() == ListView.INVALID_POSITION;
    }
}
