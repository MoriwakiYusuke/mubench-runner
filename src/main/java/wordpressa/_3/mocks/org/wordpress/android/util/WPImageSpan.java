package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.android.text.style.ImageSpan;
import wordpressa._3.mocks.org.wordpress.android.models.MediaFile;
import wordpressa._3.mocks.android.graphics.Bitmap;
import wordpressa._3.mocks.android.net.Uri;

public class WPImageSpan extends ImageSpan {
    private MediaFile mediaFile;
    private int startPosition;
    private int endPosition;
    private String imageSource;
    private boolean networkImageLoaded = false;
    private int id;
    
    public WPImageSpan() { super((android.graphics.drawable.Drawable) null); }
    
    public WPImageSpan(android.content.Context context, android.graphics.Bitmap bitmap, MediaFile mediaFile) {
        super((android.graphics.drawable.Drawable) null);
        this.mediaFile = mediaFile;
    }
    
    public WPImageSpan(Object context, android.graphics.Bitmap bitmap, MediaFile mediaFile) {
        super((android.graphics.drawable.Drawable) null);
        this.mediaFile = mediaFile;
    }
    
    // Activity, Bitmap, String コンストラクタ
    public WPImageSpan(Object context, Bitmap bitmap, String imageUri) {
        super((android.graphics.drawable.Drawable) null);
        this.imageSource = imageUri;
        this.mediaFile = new MediaFile();
    }
    
    // Activity, Bitmap, Uri コンストラクタ  
    public WPImageSpan(Object context, Bitmap bitmap, Uri imageUri) {
        super((android.graphics.drawable.Drawable) null);
        this.imageSource = imageUri != null ? imageUri.toString() : null;
        this.mediaFile = new MediaFile();
    }
    
    public WPImageSpan(android.graphics.drawable.Drawable drawable) {
        super(drawable);
    }
    
    public WPImageSpan(android.graphics.drawable.Drawable drawable, String source) {
        super(drawable, source);
        this.imageSource = source;
    }
    
    public MediaFile getMediaFile() { return mediaFile; }
    public void setMediaFile(MediaFile mediaFile) { this.mediaFile = mediaFile; }
    
    public int getStartPosition() { return startPosition; }
    public void setStartPosition(int position) { this.startPosition = position; }
    
    public int getEndPosition() { return endPosition; }
    public void setEndPosition(int position) { this.endPosition = position; }
    
    public String getImageSource() { return imageSource; }
    public void setImageSource(String source) { this.imageSource = source; }
    
    public boolean isNetworkImageLoaded() { return networkImageLoaded; }
    public void setNetworkImageLoaded(boolean loaded) { this.networkImageLoaded = loaded; }
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    private int horizontalAlignment = 0;
    
    public int getHorizontalAlignment() { return horizontalAlignment; }
    public void setHorizontalAlignment(int alignment) { this.horizontalAlignment = alignment; }
}
