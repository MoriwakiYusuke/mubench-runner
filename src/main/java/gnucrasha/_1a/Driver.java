package gnucrasha._1a;

import gnucrasha._1a.mocks.Context;
import gnucrasha._1a.mocks.GnuCashApplication;
import gnucrasha._1a.mocks.Intent;
import gnucrasha._1a.mocks.PreferenceManager;
import gnucrasha._1a.mocks.SharedPreferences;
import gnucrasha._1a.mocks.UxArgument;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Reflection-based driver for PassLockActivity variants.
 */
public class Driver {

    private final Context activity;
    private final Method onResume;
    private final Method onPause;

    public Driver(String targetClassName) {
        try {
            Class<?> clazz = Class.forName(targetClassName);
            this.activity = (Context) clazz.getDeclaredConstructor().newInstance();
            this.onResume = clazz.getDeclaredMethod("onResume");
            this.onResume.setAccessible(true);
            this.onPause = clazz.getDeclaredMethod("onPause");
            this.onPause.setAccessible(true);
        } catch (ReflectiveOperationException e) {
            throw new IllegalArgumentException("Failed to create driver for " + targetClassName, e);
        }
    }

    public Intent executeOnResume(boolean passcodeEnabled, boolean sessionActive, String action, String accountUid) {
        resetState();
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        preferences.edit().putBoolean(UxArgument.ENABLED_PASSCODE, passcodeEnabled).commit();

        Intent incoming = new Intent().setAction(action).putExtra(UxArgument.SELECTED_ACCOUNT_UID, accountUid);
        activity.setIntent(incoming);

        long baseTime = System.currentTimeMillis();
        if (sessionActive) {
            GnuCashApplication.PASSCODE_SESSION_INIT_TIME = baseTime;
        } else {
            GnuCashApplication.PASSCODE_SESSION_INIT_TIME = baseTime - GnuCashApplication.SESSION_TIMEOUT - 1000L;
        }

        invoke(onResume);
        return activity.getLastStartedIntent();
    }

    public long executeOnPause() {
        long before = System.currentTimeMillis();
        invoke(onPause);
        return GnuCashApplication.PASSCODE_SESSION_INIT_TIME - before;
    }

    private void resetState() {
        PreferenceManager.reset();
        GnuCashApplication.reset();
        activity.setIntent(new Intent());
    }

    private void invoke(Method method) {
        try {
            method.setAccessible(true);
            method.invoke(activity);
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
