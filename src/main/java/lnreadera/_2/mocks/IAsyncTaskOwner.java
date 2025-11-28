package lnreadera._2.mocks;

public interface IAsyncTaskOwner {
    void toggleProgressBar(boolean show);
    default void setMessageDialog(ICallbackEventData message) {}
    default void setMessageTextRecursive(ICallbackEventData message) {}
    default void getResult(AsyncTaskResult<?> result) {}
    default void updateProgress(String id, int current, int total, String messString) {}
    default boolean downloadListSetup(String id, String toastText, int type) { return false; }
}
