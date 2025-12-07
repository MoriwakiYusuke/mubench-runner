package wordpressa._1.mocks;

/**
 * Mock for com.simperium.client.Bucket
 */
public class Bucket<T> {
    
    public interface Listener<T> {
        void onSaveObject(Bucket<T> bucket, T object);
        void onDeleteObject(Bucket<T> bucket, T object);
        void onChange(Bucket<T> bucket, ChangeType type, String key);
        void onBeforeUpdateObject(Bucket<T> bucket, T object);
    }
    
    public enum ChangeType {
        INDEX, MODIFY, REMOVE
    }
    
    public void addListener(Listener<T> listener) {
        // no-op
    }
    
    public void removeListener(Listener<T> listener) {
        // no-op
    }
    
    @SuppressWarnings("unchecked")
    public T get(String key) throws BucketObjectMissingException {
        // Return a mock object for testing
        return (T) new BucketObject();
    }
}
