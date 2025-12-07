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

    // ========== Public Methods from NotificationsListFragment ==========
    // These methods are exposed to fulfill the "complete method coverage" requirement.

    /**
     * onCreateView - Fragment lifecycle method.
     */
    public Object onCreateView(Object inflater, Object container, Bundle savedInstanceState) {
        try {
            Method method = fragmentClass.getMethod("onCreateView", 
                Class.forName("wordpressa._1.mocks.LayoutInflater"),
                Class.forName("wordpressa._1.mocks.ViewGroup"),
                Bundle.class);
            return method.invoke(fragment, inflater, container, savedInstanceState);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onCreateView", e);
        }
    }

    /**
     * onActivityCreated - Fragment lifecycle method.
     */
    public void onActivityCreated(Bundle savedInstanceState) {
        try {
            Method method = fragmentClass.getMethod("onActivityCreated", Bundle.class);
            method.invoke(fragment, savedInstanceState);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onActivityCreated", e);
        }
    }

    /**
     * onResume - Fragment lifecycle method.
     */
    public void onResume() {
        try {
            Method method = fragmentClass.getMethod("onResume");
            method.invoke(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onResume", e);
        }
    }

    /**
     * onPause - Fragment lifecycle method.
     */
    public void onPause() {
        try {
            Method method = fragmentClass.getMethod("onPause");
            method.invoke(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onPause", e);
        }
    }

    /**
     * onDestroy - Fragment lifecycle method.
     */
    public void onDestroy() {
        try {
            Method method = fragmentClass.getMethod("onDestroy");
            method.invoke(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onDestroy", e);
        }
    }

    /**
     * onListItemClick - Called when an item in the list is clicked.
     */
    public void onListItemClick(ListView l, Object v, int position, long id) {
        try {
            Method method = fragmentClass.getMethod("onListItemClick", 
                ListView.class,
                Class.forName("wordpressa._1.mocks.View"),
                int.class, long.class);
            method.invoke(fragment, l, v, position, id);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onListItemClick", e);
        }
    }

    /**
     * setOnNoteClickListener - Set the note click listener.
     */
    public void setOnNoteClickListener(Object listener) {
        try {
            // Get the inner interface class
            Class<?> listenerClass = Class.forName(fragmentClass.getName() + "$OnNoteClickListener");
            Method method = fragmentClass.getMethod("setOnNoteClickListener", listenerClass);
            method.invoke(fragment, listener);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call setOnNoteClickListener", e);
        }
    }

    /**
     * setNoteIsHidden - Set whether a note is hidden.
     */
    public void setNoteIsHidden(String noteId, boolean isHidden) {
        try {
            Method method = fragmentClass.getMethod("setNoteIsHidden", String.class, boolean.class);
            method.invoke(fragment, noteId, isHidden);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call setNoteIsHidden", e);
        }
    }

    /**
     * setNoteIsModerating - Set whether a note is moderating.
     */
    public void setNoteIsModerating(String noteId, boolean isModerating) {
        try {
            Method method = fragmentClass.getMethod("setNoteIsModerating", String.class, boolean.class);
            method.invoke(fragment, noteId, isModerating);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call setNoteIsModerating", e);
        }
    }

    /**
     * onSaveInstanceState - Save instance state.
     */
    public void onSaveInstanceState(Bundle outState) {
        try {
            Method method = fragmentClass.getMethod("onSaveInstanceState", Bundle.class);
            method.invoke(fragment, outState);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onSaveInstanceState", e);
        }
    }

    /**
     * getScrollPosition - Get the current scroll position.
     */
    public int getScrollPosition() {
        try {
            Method method = fragmentClass.getMethod("getScrollPosition");
            return (int) method.invoke(fragment);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call getScrollPosition", e);
        }
    }

    /**
     * onSaveObject - Bucket listener callback.
     */
    public void onSaveObject(Object bucket, Object object) {
        try {
            Class<?> bucketClass = Class.forName("wordpressa._1.mocks.Bucket");
            Class<?> noteClass = Class.forName("wordpressa._1.mocks.Note");
            Method method = fragmentClass.getMethod("onSaveObject", bucketClass, noteClass);
            method.invoke(fragment, bucket, object);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onSaveObject", e);
        }
    }

    /**
     * onDeleteObject - Bucket listener callback.
     */
    public void onDeleteObject(Object bucket, Object object) {
        try {
            Class<?> bucketClass = Class.forName("wordpressa._1.mocks.Bucket");
            Class<?> noteClass = Class.forName("wordpressa._1.mocks.Note");
            Method method = fragmentClass.getMethod("onDeleteObject", bucketClass, noteClass);
            method.invoke(fragment, bucket, object);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onDeleteObject", e);
        }
    }

    /**
     * onChange - Bucket listener callback.
     */
    public void onChange(Object bucket, Object changeType, String key) {
        try {
            Class<?> bucketClass = Class.forName("wordpressa._1.mocks.Bucket");
            Class<?> changeTypeClass = Class.forName("wordpressa._1.mocks.Bucket$ChangeType");
            Method method = fragmentClass.getMethod("onChange", bucketClass, changeTypeClass, String.class);
            method.invoke(fragment, bucket, changeType, key);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onChange", e);
        }
    }

    /**
     * onBeforeUpdateObject - Bucket listener callback.
     */
    public void onBeforeUpdateObject(Object bucket, Object object) {
        try {
            Class<?> bucketClass = Class.forName("wordpressa._1.mocks.Bucket");
            Class<?> noteClass = Class.forName("wordpressa._1.mocks.Note");
            Method method = fragmentClass.getMethod("onBeforeUpdateObject", bucketClass, noteClass);
            method.invoke(fragment, bucket, object);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Failed to call onBeforeUpdateObject", e);
        }
    }
}
