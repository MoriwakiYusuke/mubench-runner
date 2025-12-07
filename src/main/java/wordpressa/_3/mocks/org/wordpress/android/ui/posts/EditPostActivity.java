package wordpressa._3.mocks.org.wordpress.android.ui.posts;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.app.Activity;
import wordpressa._3.mocks.android.app.Intent;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.org.wordpress.android.models.Post;
import wordpressa._3.mocks.com.actionbarsherlock.app.ActionBar;

public interface EditPostActivity {
    Post getPost();
    void setLocalDraft(boolean isLocalDraft);
    void savePost(boolean isAutosave);
    Activity getActivity();
    ActionBar getSupportActionBar();
    void showPostSettings();
    Intent getIntent();
    String getString(int resId);
    void supportInvalidateOptionsMenu();
    void finish();
    void startActivityForResult(Intent intent, int requestCode);
    String getStatEventEditorClosed();
    Object getSystemService(String name);
    wordpressa._3.mocks.android.view.Window getWindow();
}
