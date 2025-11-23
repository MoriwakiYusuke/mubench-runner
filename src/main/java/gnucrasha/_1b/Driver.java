package gnucrasha._1b;

import gnucrasha._1b.mocks.Context;
import gnucrasha._1b.mocks.GnuCashApplication;
import gnucrasha._1b.mocks.Intent;
import gnucrasha._1b.mocks.PreferenceManager;
import gnucrasha._1b.mocks.SharedPreferences;
import gnucrasha._1b.mocks.UxArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for PasscodeLockScreenActivity variants.
 */
public class Driver {

    private final Context activity;
    private final Method onPasscodeEntered;
    private final Method onBackPressed;

    public Driver(String targetClassName) {
        try {
            Class<?> clazz = Class.forName(targetClassName);
            this.activity = (Context) clazz.getDeclaredConstructor().newInstance();
            this.onPasscodeEntered = clazz.getDeclaredMethod("onPasscodeEntered", String.class);
            this.onBackPressed = clazz.getDeclaredMethod("onBackPressed");
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    public Intent submitPasscode(String storedPasscode, String enteredPasscode, String callerClass, String action, String accountUid) {
        resetState();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        preferences.edit().putString(UxArgument.PASSCODE, storedPasscode).commit();

        Intent incoming = new Intent().setAction(action)
                .putExtra(UxArgument.PASSCODE_CLASS_CALLER, callerClass)
                .putExtra(UxArgument.SELECTED_ACCOUNT_UID, accountUid);
        activity.setIntent(incoming);

        invoke(onPasscodeEntered, enteredPasscode);
        return activity.getLastStartedIntent();
    }

    public Intent pressBack() {
        resetState();
        invoke(onBackPressed);
        return activity.getLastStartedIntent();
    }

    private void resetState() {
        PreferenceManager.reset();
        GnuCashApplication.reset();
        activity.setIntent(new Intent());
    }

    private void invoke(Method method, Object... args) {
        try {
            method.invoke(activity, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if (cause instanceof RuntimeException) {
                throw (RuntimeException) cause;
            }
            throw new RuntimeException(cause);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
