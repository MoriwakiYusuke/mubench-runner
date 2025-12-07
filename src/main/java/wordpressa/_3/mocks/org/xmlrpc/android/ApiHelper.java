package wordpressa._3.mocks.org.xmlrpc.android;

public class ApiHelper {
    public static class EditMediaItemTask {
        public EditMediaItemTask() {}
        public EditMediaItemTask(String mediaId, String title, String description, String caption, GenericCallback callback) {}
        public void execute(Object... params) {}
    }
    
    public static class GetMediaItemTask {
        public void execute(Object... params) {}
    }
    
    public enum ErrorType {
        NO_ERROR, GENERIC_ERROR, INVALID_CURRENT_BLOG, NETWORK_XMLRPC
    }
    
    public interface GenericCallback {
        void onSuccess();
        void onFailure(ErrorType errorType, String errorMessage, Throwable throwable);
    }
}
