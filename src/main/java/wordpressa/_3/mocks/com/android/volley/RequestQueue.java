package wordpressa._3.mocks.com.android.volley;

public class RequestQueue {
    public void add(Request<?> request) {}
    public void start() {}
    public void stop() {}
    public void cancelAll(Object tag) {}
    public void cancelAll(RequestFilter filter) {}
    
    public interface RequestFilter {
        boolean apply(Request<?> request);
    }
}
