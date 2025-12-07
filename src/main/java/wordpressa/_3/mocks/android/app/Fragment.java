package wordpressa._3.mocks.android.app;

import wordpressa._3.mocks.android.view.LayoutInflater;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.view.ViewGroup;
import wordpressa._3.mocks.android.view.MenuItem;
import wordpressa._3.mocks.android.os.Bundle;
import wordpressa._3.mocks.android.content.res.Configuration;

public class Fragment {
    protected Activity mActivity;
    protected boolean isAdded = true;
    
    public Activity getActivity() { return mActivity; }
    public void setActivity(Activity activity) { this.mActivity = activity; }
    
    public boolean isAdded() { return isAdded; }
    public void setIsAdded(boolean added) { this.isAdded = added; }
    
    public String getString(int resId) { return ""; }
    public View getView() { return null; }
    public Object getSystemService(String name) { return null; }
    
    public void onCreate(Bundle savedInstanceState) {}
    public void onActivityCreated(Bundle savedInstanceState) {}
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { return null; }
    public void onAttach(Activity activity) { this.mActivity = activity; }
    public void onDetach() {}
    public void onResume() {}
    public void onPause() {}
    public void onDestroy() {}
    public void onSaveInstanceState(Bundle outState) {}
    public void onActivityResult(int requestCode, int resultCode, Intent data) {}
    public void registerForContextMenu(View view) {}
    public void onViewCreated(View view, Bundle savedInstanceState) {}
    public android.content.res.Resources getResources() { return null; }
    public void onConfigurationChanged(Configuration newConfig) {}
    public void startActivityForResult(Intent intent, int requestCode) {
        if (mActivity != null) {
            mActivity.startActivityForResult(intent, requestCode);
        }
    }
    public boolean onContextItemSelected(MenuItem item) { return false; }
}
