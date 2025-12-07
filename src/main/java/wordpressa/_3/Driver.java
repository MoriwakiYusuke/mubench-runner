package wordpressa._3;

import wordpressa._3.mocks.android.text.Editable;
import wordpressa._3.mocks.android.text.SpannableStringBuilder;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.org.wordpress.android.R;
import wordpressa._3.mocks.org.wordpress.android.util.WPEditText;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Driver for wordpressa Case 3: EditPostContentFragment
 * 
 * Bug: In onClick() method (around line 1098), when handling R.id.more,
 * mSelectionEnd may exceed str.length() causing StringIndexOutOfBoundsException.
 * 
 * Original: has bounds check "if (mSelectionEnd > str.length()) mSelectionEnd = str.length();"
 * Misuse: missing the bounds check
 * 
 * This driver uses dynamic testing via reflection.
 */
public class Driver {

    private static final String BASE_PACKAGE = "wordpressa._3";
    
    private final Object fragment;
    private final Class<?> fragmentClass;

    public Driver(String variant) {
        try {
            String className = BASE_PACKAGE + "." + variant + ".EditPostContentFragment";
            this.fragmentClass = Class.forName(className);
            this.fragment = fragmentClass.getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for variant: " + variant, e);
        }
    }

    /**
     * Test the bounds check bug scenario.
     * 
     * Sets up:
     * - mContentEditText with text of length 5
     * - selectionEnd = 100 (greater than text length)
     * - Triggers onClick with R.id.more
     * 
     * Original should handle this gracefully (bounds check).
     * Misuse should throw StringIndexOutOfBoundsException.
     * 
     * @return true if no exception (bounds check present), false if exception thrown
     */
    public boolean testOnClickMoreButton() {
        try {
            // Create WPEditText with short text but large selectionEnd
            WPEditText editText = new WPEditText();
            SpannableStringBuilder text = new SpannableStringBuilder("hello"); // length = 5
            editText.setText(text);
            editText.setSelection(100); // selectionEnd = 100 > text.length()
            
            // Set mContentEditText field
            Field contentEditTextField = fragmentClass.getDeclaredField("mContentEditText");
            contentEditTextField.setAccessible(true);
            contentEditTextField.set(fragment, editText);
            
            // Set mActivity field with a mock
            Field activityField = fragmentClass.getDeclaredField("mActivity");
            activityField.setAccessible(true);
            activityField.set(fragment, createMockEditPostActivity());
            
            // Get mFormatBarButtonClickListener field
            Field listenerField = fragmentClass.getDeclaredField("mFormatBarButtonClickListener");
            listenerField.setAccessible(true);
            Object listener = listenerField.get(fragment);
            
            if (listener == null) {
                throw new RuntimeException("mFormatBarButtonClickListener is null");
            }
            
            // Create a mock View with id = R.id.more
            View mockView = new View();
            mockView.setId(R.id.more);
            
            // Find onClick method on the listener object
            Method onClickMethod = listener.getClass().getMethod("onClick", View.class);
            onClickMethod.setAccessible(true);
            onClickMethod.invoke(listener, mockView);
            
            return true; // No exception = bounds check present
            
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof StringIndexOutOfBoundsException || 
                cause instanceof IndexOutOfBoundsException) {
                return false; // Exception = bounds check missing
            }
            // Other exceptions are test setup issues
            throw new RuntimeException("Unexpected exception during test", e);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("Test setup failed", e);
        }
    }
    
    /**
     * Create a mock EditPostActivity using Proxy.
     */
    private Object createMockEditPostActivity() {
        return java.lang.reflect.Proxy.newProxyInstance(
            Driver.class.getClassLoader(),
            new Class<?>[] { wordpressa._3.mocks.org.wordpress.android.ui.posts.EditPostActivity.class },
            (proxy, method, args) -> {
                String methodName = method.getName();
                if ("getStatEventEditorClosed".equals(methodName)) {
                    return "event_closed";
                }
                if ("getSupportActionBar".equals(methodName)) {
                    return new wordpressa._3.mocks.com.actionbarsherlock.app.ActionBar();
                }
                if ("getPost".equals(methodName)) {
                    return new wordpressa._3.mocks.org.wordpress.android.models.Post();
                }
                return null;
            }
        );
    }

    /**
     * Check if bounds check is present by testing with edge case.
     * This is the main test method that CommonLogic should call.
     */
    public boolean hasBoundsCheckForSelectionEnd() {
        return testOnClickMoreButton();
    }
}
