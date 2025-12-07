package wordpressa._1.mocks;

/**
 * Mock for org.wordpress.android.util.ptr.PullToRefreshHelper
 */
public class PullToRefreshHelper {
    
    public interface RefreshListener {
        void onRefreshStarted(View view);
    }
    
    public PullToRefreshHelper(Activity activity, PullToRefreshLayout layout, RefreshListener listener, Class<?> headerViewClass) {
        // no-op
    }
    
    public void registerReceiver(Activity activity) {
        // no-op
    }
    
    public void unregisterReceiver(Activity activity) {
        // no-op
    }
    
    public void setRefreshing(boolean refreshing) {
        // no-op
    }
}
