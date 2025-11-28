package lnreadera._1.mocks;

/**
 * Mock SherlockActivity that tracks whether super.onDestroy() is called.
 */
public class SherlockActivity {

    private boolean superOnDestroyCalled = false;

    protected void onDestroy() {
        superOnDestroyCalled = true;
    }

    public boolean isSuperOnDestroyCalled() {
        return superOnDestroyCalled;
    }

    // Stub methods required by subclass
    protected void onCreate(Bundle savedInstanceState) {}
    protected void onStop() {}
    protected void onBackPressed() {}
    public void setTitle(String title) {}
    public void startActivity(Intent intent) {}
    public Object getApplicationContext() { return this; }
    public Intent getIntent() { return new Intent(); }
    public Resources getResources() { return new Resources(); }
    public Object findViewById(int id) { return new NonLeakingWebView(); }
    public MenuInflater getSupportMenuInflater() { return new MenuInflater(); }
    public boolean onCreateOptionsMenu(Menu menu) { return true; }
    public boolean onOptionsItemSelected(MenuItem item) { return false; }
}
