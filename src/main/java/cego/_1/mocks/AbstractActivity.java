package cego._1.mocks;

/**
 * Minimal activity that records the last started intent.
 */
public class AbstractActivity extends Context {

    private Intent lastStartedIntent;

    protected void startActivity(Intent intent) {
        this.lastStartedIntent = intent;
    }

    public Intent getLastStartedIntent() {
        return lastStartedIntent;
    }
}
