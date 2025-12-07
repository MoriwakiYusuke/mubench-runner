package wordpressa._3;

import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.Spannable;
import wordpressa._3.mocks.android.text.SpannableStringBuilder;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.widget.ToggleButton;
import wordpressa._3.mocks.org.wordpress.android.util.WPEditText;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

/**
 * Driver for wordpressa Case 3: EditPostContentFragment
 * 
 * Bug: In onFormatButtonClick(), mContentEditText.getText() is called twice:
 * 1. First as Spannable s = getText() with null check
 * 2. Later as Editable content = getText() WITHOUT null check (in isLocalDraft=false path)
 * 
 * If getText() returns null on the second call, NPE occurs.
 */
public class Driver {

    /**
     * Tests the onFormatButtonClick method which may throw NPE when getText() returns null
     * on the second call (in the isLocalDraft=false code path).
     */
    public static boolean testOnFormatButtonClick(Class<?> fragmentClass) {
        try {
            Object fragment = fragmentClass.getDeclaredConstructor().newInstance();
            
            // Create WPEditText that returns non-null first, then null
            WPEditText specialEditText = new WPEditText() {
                private int callCount = 0;
                private SpannableStringBuilder validText = new SpannableStringBuilder("test");
                
                @Override
                public Editable getText() {
                    callCount++;
                    if (callCount <= 1) {
                        return validText;
                    }
                    return null;
                }
                
                @Override
                public int getSelectionStart() {
                    return 0;
                }
                
                @Override
                public int getSelectionEnd() {
                    return 5;
                }
                
                @Override
                public void setSelection(int start, int stop) {}
                
                @Override
                public void setSelection(int index) {}
            };
            
            Field mContentEditTextField = fragmentClass.getDeclaredField("mContentEditText");
            mContentEditTextField.setAccessible(true);
            mContentEditTextField.set(fragment, specialEditText);
            
            Field mActivityField = fragmentClass.getDeclaredField("mActivity");
            mActivityField.setAccessible(true);
            mActivityField.set(fragment, createMockEditPostActivity(false));
            
            ToggleButton toggleButton = new ToggleButton();
            toggleButton.setChecked(true);
            
            Method onFormatButtonClick = fragmentClass.getDeclaredMethod(
                "onFormatButtonClick", ToggleButton.class, String.class);
            onFormatButtonClick.setAccessible(true);
            
            onFormatButtonClick.invoke(fragment, toggleButton, "strong");
            
            return true;
            
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NullPointerException) {
                return false;
            }
            throw new RuntimeException("Unexpected exception during test", e);
        } catch (Exception e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }
    
    private static Object createMockEditPostActivity(boolean isLocalDraft) {
        return java.lang.reflect.Proxy.newProxyInstance(
            Driver.class.getClassLoader(),
            new Class<?>[] { wordpressa._3.mocks.org.wordpress.android.ui.posts.EditPostActivity.class },
            (proxy, method, args) -> {
                String methodName = method.getName();
                if ("getPost".equals(methodName)) {
                    wordpressa._3.mocks.org.wordpress.android.models.Post post = 
                        new wordpressa._3.mocks.org.wordpress.android.models.Post();
                    post.setLocalDraft(isLocalDraft);
                    return post;
                }
                if ("getSupportActionBar".equals(methodName)) {
                    return new wordpressa._3.mocks.com.actionbarsherlock.app.ActionBar();
                }
                if ("getStatEventEditorClosed".equals(methodName)) {
                    return "event_closed";
                }
                return null;
            }
        );
    }
}
