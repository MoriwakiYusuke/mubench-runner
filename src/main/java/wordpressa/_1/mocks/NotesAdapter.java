package wordpressa._1.mocks;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Mock for org.wordpress.android.ui.notifications.NotesAdapter
 */
public class NotesAdapter {
    private List<Note> notes = new ArrayList<>();
    private Set<String> hiddenNoteIds = new HashSet<>();
    private Set<String> moderatingNoteIds = new HashSet<>();
    private boolean cursorClosed = false;
    
    public NotesAdapter(Activity activity, Bucket<Note> bucket) {
        // Initialize with some default notes
        for (int i = 0; i < 5; i++) {
            notes.add(new Note());
        }
    }
    
    public int getCount() {
        return notes.size();
    }
    
    public void setCount(int count) {
        notes.clear();
        for (int i = 0; i < count; i++) {
            notes.add(new Note());
        }
    }
    
    public Note getNote(int position) {
        if (position < 0 || position >= notes.size()) {
            return null;
        }
        return notes.get(position);
    }
    
    public void closeCursor() {
        cursorClosed = true;
    }
    
    public boolean isCursorClosed() {
        return cursorClosed;
    }
    
    public void reloadNotes() {
        // no-op for mock
    }
    
    public void addHiddenNoteId(String noteId) {
        hiddenNoteIds.add(noteId);
    }
    
    public void removeHiddenNoteId(String noteId) {
        hiddenNoteIds.remove(noteId);
    }
    
    public void addModeratingNoteId(String noteId) {
        moderatingNoteIds.add(noteId);
    }
    
    public void removeModeratingNoteId(String noteId) {
        moderatingNoteIds.remove(noteId);
    }
    
    public boolean isModeratingNote(String noteId) {
        return moderatingNoteIds.contains(noteId);
    }
}
