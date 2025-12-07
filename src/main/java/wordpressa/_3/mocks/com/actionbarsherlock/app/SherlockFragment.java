package wordpressa._3.mocks.com.actionbarsherlock.app;

import wordpressa._3.mocks.android.app.Activity;
import wordpressa._3.mocks.android.app.Fragment;
import wordpressa._3.mocks.android.app.Intent;
import wordpressa._3.mocks.android.view.LayoutInflater;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.view.ViewGroup;
import wordpressa._3.mocks.android.os.Bundle;
import wordpressa._3.mocks.android.content.res.Configuration;

public class SherlockFragment extends Fragment {
    
    public SherlockFragment() {}
    
    public SherlockActivity getSherlockActivity() {
        return (SherlockActivity) getActivity();
    }
    
    // @Override メソッドを追加
    public void onStart() {}
    public void onStop() {}
    public boolean onOptionsItemSelected(Object item) { return false; }
}
