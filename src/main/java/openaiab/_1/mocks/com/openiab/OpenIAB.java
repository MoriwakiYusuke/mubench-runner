package openaiab._1.mocks.com.openiab;

import openaiab._1.requirements.Logger;

/**
 * Minimal wrapper that mimics the original OpenIAB singleton used by BillingActivity.
 */
public final class OpenIAB {

    public static final String TAG = Logger.LOG_TAG;

    private static final OpenIAB INSTANCE = new OpenIAB();

    private OpenIABHelper helper = new OpenIABHelper();

    private OpenIAB() {
        // no-op
    }

    public static OpenIAB instance() {
        return INSTANCE;
    }

    public OpenIABHelper getHelper() {
        return helper;
    }

    public void overrideHelper(OpenIABHelper helper) {
        this.helper = helper == null ? new OpenIABHelper() : helper;
    }

    public void unbindService() {
        helper.unbindService();
    }
}
