package wordpressa._3.mocks.org.wordpress.android.util;

import wordpressa._3.mocks.org.wordpress.android.models.MediaGallery;

public class MediaGalleryImageSpan {
    private MediaGallery gallery;
    
    public MediaGalleryImageSpan() {}
    public MediaGalleryImageSpan(android.content.Context context, android.graphics.drawable.Drawable drawable, MediaGallery gallery) {
        this.gallery = gallery;
    }
    public MediaGalleryImageSpan(Object context, MediaGallery gallery) {
        this.gallery = gallery;
    }
    
    public MediaGallery getMediaGallery() { return gallery; }
    public void setMediaGallery(MediaGallery gallery) { this.gallery = gallery; }
}
