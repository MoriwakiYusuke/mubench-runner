package lnreadera._1.mocks;

public class LoadImageTask {
    public LoadImageTask(boolean refresh, Object owner) {}
    public Status getStatus() { return Status.FINISHED; }
    public void cancel(boolean mayInterruptIfRunning) {}
    public void execute(String[] params) {}
    public void executeOnExecutor(Object executor, String[] params) {}

    public enum Status {
        PENDING, RUNNING, FINISHED
    }
}
