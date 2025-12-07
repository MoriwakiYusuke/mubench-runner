package wordpressa._1.mocks;

/**
 * Mock for org.wordpress.android.ui.notifications.utils.SimperiumUtils
 */
public class SimperiumUtils {
    private static Bucket<Note> notesBucket = new Bucket<>();
    private static Bucket<BucketObject> metaBucket = new Bucket<>();
    
    public static Bucket<Note> getNotesBucket() {
        return notesBucket;
    }
    
    public static Bucket<BucketObject> getMetaBucket() {
        return metaBucket;
    }
    
    public static void setNotesBucket(Bucket<Note> bucket) {
        notesBucket = bucket;
    }
    
    public static void setMetaBucket(Bucket<BucketObject> bucket) {
        metaBucket = bucket;
    }
    
    public static void reset() {
        notesBucket = new Bucket<>();
        metaBucket = new Bucket<>();
    }
}
