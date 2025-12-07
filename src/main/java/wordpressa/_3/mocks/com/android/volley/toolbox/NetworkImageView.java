package wordpressa._3.mocks.com.android.volley.toolbox;

import wordpressa._3.mocks.android.graphics.Bitmap;
import wordpressa._3.mocks.android.widget.ImageView;

public class NetworkImageView extends ImageView {
    private String imageUrl;
    
    public void setImageUrl(String url, ImageLoader imageLoader) {
        this.imageUrl = url;
    }
    
    public void setDefaultImageResId(int defaultImage) {}
    public void setErrorImageResId(int errorImage) {}
}
