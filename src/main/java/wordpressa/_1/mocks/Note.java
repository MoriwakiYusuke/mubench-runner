package wordpressa._1.mocks;

/**
 * Mock for org.wordpress.android.models.Note
 */
public class Note {
    private String id;
    private long timestamp;
    
    public Note() {
        this.id = "note-" + System.nanoTime();
        this.timestamp = System.currentTimeMillis();
    }
    
    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
