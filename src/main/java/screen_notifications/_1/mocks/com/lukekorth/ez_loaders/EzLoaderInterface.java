package screen_notifications._1.mocks.com.lukekorth.ez_loaders;

import screen_notifications._1.mocks.android.os.Bundle;
import screen_notifications._1.mocks.android.support.v4.content.Loader;

/**
 * Stub for EzLoaderInterface from lukekorth's ez-loaders library.
 */
public interface EzLoaderInterface<D> {
    Loader<D> onCreateLoader(int id, Bundle args);
    D loadInBackground(int id);
    void onLoadFinished(Loader<D> loader, D data);
    void onLoaderReset(Loader<D> loader);
    void onReleaseResources(D data);
}
