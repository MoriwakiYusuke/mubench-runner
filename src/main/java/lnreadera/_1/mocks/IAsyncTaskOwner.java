package lnreadera._1.mocks;

public interface IAsyncTaskOwner {
    void toggleProgressBar(boolean show);
    void setMessageDialog(ICallbackEventData message);
    void getResult(AsyncTaskResult<?> result);
    void updateProgress(String id, int current, int total, String messString);
    boolean downloadListSetup(String id, String toastText, int type);
}
