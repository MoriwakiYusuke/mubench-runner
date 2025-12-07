package tucanmobile._1.mocks;

import tucanmobile._1.requirements.AnswerObject;
import tucanmobile._1.requirements.RequestObject;

/**
 * Mock for android.os.AsyncTask - stub implementation for testing.
 */
public abstract class AsyncTask<Params, Progress, Result> {
    
    protected abstract Result doInBackground(Params... params);
    
    protected void onPreExecute() {
    }
    
    protected void onPostExecute(Result result) {
    }
    
    protected void onProgressUpdate(Progress... values) {
    }
    
    public final AsyncTask<Params, Progress, Result> execute(Params... params) {
        onPreExecute();
        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }
}
