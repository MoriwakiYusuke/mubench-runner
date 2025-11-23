package cego._1.misuse;

import cego._1.mocks.AbstractActivity;
import cego._1.mocks.Bitmap;
import cego._1.mocks.BitmapDrawable;
import cego._1.mocks.Intent;
import cego._1.mocks.LocalStorage;
import cego._1.mocks.Log;
import cego._1.mocks.Settings;
import cego._1.mocks.Uri;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Misuse variant with the incorrect MIME type when opening images.
 */
public class cgeoimages extends AbstractActivity {

    private BitmapDrawable currentDrawable;

    public void setCurrentDrawable(BitmapDrawable drawable) {
        this.currentDrawable = drawable;
    }

    public void openCurrentDrawable() {
        viewImageInStandardApp(currentDrawable);
    }

    public Intent fetchLastStartedIntent() {
        return getLastStartedIntent();
    }

    private void viewImageInStandardApp(final BitmapDrawable image) {
        if (image == null) {
            Log.e(Settings.tag, "cgeoimages.handleMessage.onClick: missing image");
            return;
        }

        final File file = LocalStorage.getStorageFile(null, "temp.jpg", false, true);
        try (FileOutputStream fos = new FileOutputStream(file)) {
            image.getBitmap().compress(Bitmap.CompressFormat.JPEG, 100, fos);
        } catch (Exception e) {
            Log.e(Settings.tag, "cgeoimages.handleMessage.onClick: " + e.toString());
            return;
        }

        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "image/jpg");
        startActivity(intent);

        if (file.exists()) {
            file.deleteOnExit();
        }
    }
}
