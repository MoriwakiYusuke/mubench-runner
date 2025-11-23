package gnucrasha._1a.mocks;

/**
 * Minimal context implementation tracking the current and last-started intents.
 */
public class Context {

    private Intent intent = new Intent();
    private Intent lastStartedIntent;

    public Context getApplicationContext() {
        return this;
    }

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public void startActivity(Intent intent) {
        this.lastStartedIntent = intent;
    }

    public Intent getLastStartedIntent() {
        return lastStartedIntent;
    }
}
