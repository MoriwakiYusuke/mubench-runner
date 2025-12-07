package wordpressa._3.mocks.org.wordpress.android.ui.media;

import wordpressa._3.mocks.android.net.Uri;
import wordpressa._3.mocks.android.graphics.Bitmap;
import wordpressa._3.mocks.org.wordpress.android.models.MediaFile;
import wordpressa._3.mocks.org.wordpress.android.util.WPImageSpan;
import java.util.List;
import java.util.ArrayList;

public class MediaUtils {
    
    public static class RequestCode {
        public static final int ACTIVITY_REQUEST_CODE_PICTURE_LIBRARY = 1000;
        public static final int ACTIVITY_REQUEST_CODE_TAKE_PHOTO = 1001;
        public static final int ACTIVITY_REQUEST_CODE_VIDEO_LIBRARY = 1002;
        public static final int ACTIVITY_REQUEST_CODE_TAKE_VIDEO = 1003;
        public static final int ACTIVITY_REQUEST_CODE_MEDIA_LIBRARY = 1004;
        public static final int ACTIVITY_REQUEST_CODE_MEDIA_GALLERY = 1005;
        public static final int ACTIVITY_REQUEST_CODE_MEDIA_UPLOAD = 1006;
    }
    
    public interface LaunchCameraCallback {
        void onMediaCapturePathReady(String mediaCapturePath);
    }
    
    public interface GenericCallback {
        void onComplete();
    }
    
    public enum ErrorType {
        NO_ERROR, GENERIC_ERROR, UNSUPPORTED_FORMAT
    }
    
    public static boolean isValidImage(String url) { return url != null && url.length() > 0; }
    public static boolean isVideo(String url) { return url != null && url.endsWith(".mp4"); }
    public static boolean isImage(String url) { return url != null; }
    public static String getMimeType(Uri uri) { return "image/jpeg"; }
    public static String getMimeTypeFromUri(Object context, Uri uri) { return "image/jpeg"; }
    public static String getMediaFileName(Object context, Uri uri) { return "file"; }
    public static String getMediaType(String mimeType) { return "image"; }
    public static Uri getLastRecordedVideoUri(Object context) { return new Uri(""); }
    public static void launchCamera(Object activity, LaunchCameraCallback callback) {
        if (callback != null) callback.onMediaCapturePathReady("");
    }
    public static void launchVideoCamera(Object activity, LaunchCameraCallback callback) {
        if (callback != null) callback.onMediaCapturePathReady("");
    }
    public static void launchVideoCamera(Object activity) {}
    public static void launchPictureLibrary(Object activity) {}
    public static void launchVideoLibrary(Object activity) {}
    public static List<MediaFile> getMediaFilesFromContent(Object context, String content) { return new ArrayList<>(); }
    public static void setWaitingForThis(Object obj) {}
    public static Object getWaitingForThis() { return null; }
    public static Uri getOptimizedMedia(Object context, String path) { return null; }
    public static boolean isLocalFile(String url) { return false; }
    public static String getPath(Object context, Uri uri) { return ""; }
    public static boolean isInMediaStore(Uri uri) { return false; }
    public static Uri downloadExternalMedia(Object activity, Uri uri) { return uri; }
    public static WPImageSpan prepareWPImageSpan(Object activity, String imagePath, String imageUri) { return new WPImageSpan(); }
    public static byte[] getImageBytesForPath(String path, Object activity) { return new byte[0]; }
    public static Bitmap getThumbnailForWPImageSpan(Object activity, byte[] bytes, String imageUri) { return null; }
    public static Bitmap getThumbnailForWPImageSpan(Bitmap bitmap, int maxSize) { return bitmap; }
    public static void setWPImageSpanWidth(Object activity, Uri uri, WPImageSpan span) {}
    
    public static int getMinimumImageWidth(Object activity, String imagePath) { return 100; }
}
