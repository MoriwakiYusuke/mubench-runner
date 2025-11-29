package openaiab._1.mocks.com.unity3d.player;

import android.content.Intent;

/**
 * Simplified Unity activity that tracks whether subclasses call through to {@link #onDestroy()}.
 */
public class UnityPlayerActivity {

    private boolean unityOnDestroyCalled;

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // no-op stub
    }

    protected void onDestroy() {
        unityOnDestroyCalled = true;
    }

    public boolean wasUnityOnDestroyCalled() {
        return unityOnDestroyCalled;
    }

    public void resetUnityOnDestroyFlag() {
        unityOnDestroyCalled = false;
    }
}
