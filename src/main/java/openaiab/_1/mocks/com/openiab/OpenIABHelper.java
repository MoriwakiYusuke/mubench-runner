package openaiab._1.mocks.com.openiab;

import android.content.Intent;

/**
 * Minimal helper stub that allows tests to control how activity results are handled and records unbind events.
 */
public class OpenIABHelper {

    private boolean handleActivityResult;
    private boolean unbound;

    public boolean handleActivityResult(int requestCode, int resultCode, Intent data) {
        return handleActivityResult;
    }

    public void setHandleActivityResult(boolean handleActivityResult) {
        this.handleActivityResult = handleActivityResult;
    }

    public void unbindService() {
        this.unbound = true;
    }

    public boolean wasUnbound() {
        return unbound;
    }

    public void reset() {
        this.unbound = false;
    }
}
