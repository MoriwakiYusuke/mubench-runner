package tucanmobile._1;

import tucanmobile._1.mocks.ProgressDialog;
import tucanmobile._1.mocks.SimpleWebActivity;
import tucanmobile._1.mocks.SimpleWebListActivity;
import tucanmobile._1.requirements.AnswerObject;
import tucanmobile._1.requirements.RequestObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for SimpleSecureBrowser variants.
 */
public class Driver {

    private final Class<?> targetClass;
    private Object instance;

    /**
     * Default constructor - uses SimpleWebListActivity.
     */
    public Driver(String targetClassName) {
        this(targetClassName, new SimpleWebListActivity());
    }

    /**
     * Constructor with SimpleWebListActivity (mirrors original constructor).
     */
    public Driver(String targetClassName, SimpleWebListActivity callingActivity) {
        try {
            this.targetClass = Class.forName(targetClassName);
            Constructor<?> ctor = targetClass.getConstructor(SimpleWebListActivity.class);
            this.instance = ctor.newInstance(callingActivity);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    /**
     * Constructor with SimpleWebActivity (mirrors original constructor).
     */
    public Driver(String targetClassName, SimpleWebActivity callingActivity) {
        try {
            this.targetClass = Class.forName(targetClassName);
            Constructor<?> ctor = targetClass.getConstructor(SimpleWebActivity.class);
            this.instance = ctor.newInstance(callingActivity);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    /**
     * Execute the AsyncTask flow: onPreExecute -> doInBackground -> onPostExecute.
     */
    public Object execute(RequestObject... params) {
        try {
            Method executeMethod = targetClass.getMethod("execute", Object[].class);
            return executeMethod.invoke(instance, (Object) params);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Get the dialog field for testing purposes.
     */
    public ProgressDialog getDialog() {
        try {
            Field dialogField = targetClass.getDeclaredField("dialog");
            dialogField.setAccessible(true);
            return (ProgressDialog) dialogField.get(instance);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Set the dialog field for testing purposes.
     */
    public void setDialog(ProgressDialog dialog) {
        try {
            Field dialogField = targetClass.getDeclaredField("dialog");
            dialogField.setAccessible(true);
            dialogField.set(instance, dialog);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke onPreExecute directly for testing.
     */
    public void invokeOnPreExecute() {
        try {
            Method method = targetClass.getDeclaredMethod("onPreExecute");
            method.setAccessible(true);
            method.invoke(instance);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke onPostExecute directly for testing.
     */
    public void invokeOnPostExecute(AnswerObject result) {
        try {
            Method method = targetClass.getDeclaredMethod("onPostExecute", Object.class);
            method.setAccessible(true);
            method.invoke(instance, result);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Invoke doInBackground directly for testing.
     */
    public AnswerObject invokeDoInBackground(RequestObject... requestInfo) {
        try {
            Method method = targetClass.getDeclaredMethod("doInBackground", Object[].class);
            method.setAccessible(true);
            return (AnswerObject) method.invoke(instance, (Object) requestInfo);
        } catch (InvocationTargetException e) {
            throw rethrow(e.getCause());
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private RuntimeException rethrow(Throwable t) {
        if (t instanceof RuntimeException runtime) {
            return runtime;
        }
        return new RuntimeException(t);
    }
}
