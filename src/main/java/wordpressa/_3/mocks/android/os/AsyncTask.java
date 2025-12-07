package wordpressa._3.mocks.android.os;

public class AsyncTask<Params, Progress, Result> {
    
    public static final java.util.concurrent.Executor THREAD_POOL_EXECUTOR = null;
    public static final java.util.concurrent.Executor SERIAL_EXECUTOR = null;
    
    protected Result doInBackground(Params... params) { return null; }
    protected void onPreExecute() {}
    protected void onPostExecute(Result result) {}
    protected void onProgressUpdate(Progress... values) {}
    protected void onCancelled() {}
    protected void onCancelled(Result result) {}
    
    protected void publishProgress(Progress... values) {}
    
    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        onPreExecute();
        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }
    
    public final AsyncTask<Params, Progress, Result> executeOnExecutor(java.util.concurrent.Executor exec, Params... params) {
        return execute(params);
    }
    
    public final boolean cancel(boolean mayInterruptIfRunning) { return false; }
    public final boolean isCancelled() { return false; }
    
    public enum Status {
        PENDING, RUNNING, FINISHED
    }
    
    public final Status getStatus() { return Status.PENDING; }
}
