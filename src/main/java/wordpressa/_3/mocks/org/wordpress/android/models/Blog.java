package wordpressa._3.mocks.org.wordpress.android.models;

public class Blog {
    private int localTableBlogId;
    private String blogName;
    private String url;
    private String homeUrl;
    private String username;
    private String password;
    private String imageUrl;
    private int remoteBlogId;
    private boolean dotcomFlag;
    private boolean admin;
    private boolean hidden;
    private String httpUser;
    private String httpPassword;
    private boolean scaledImage;
    private int scaledImageWidth;
    private int maxImageWidth;
    private String maxImageWidthId;
    private boolean fullSizeImage;
    private String postFormats;
    
    public int getLocalTableBlogId() { return localTableBlogId; }
    public void setLocalTableBlogId(int id) { this.localTableBlogId = id; }
    
    public String getBlogName() { return blogName; }
    public void setBlogName(String name) { this.blogName = name; }
    
    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }
    
    public String getHomeUrl() { return homeUrl; }
    public void setHomeUrl(String homeUrl) { this.homeUrl = homeUrl; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public int getRemoteBlogId() { return remoteBlogId; }
    public void setRemoteBlogId(int id) { this.remoteBlogId = id; }
    
    public boolean isDotcomFlag() { return dotcomFlag; }
    public void setDotcomFlag(boolean flag) { this.dotcomFlag = flag; }
    
    public boolean isAdmin() { return admin; }
    public void setAdmin(boolean admin) { this.admin = admin; }
    
    public boolean isHidden() { return hidden; }
    public void setHidden(boolean hidden) { this.hidden = hidden; }
    
    public boolean isScaledImage() { return scaledImage; }
    public void setScaledImage(boolean scaledImage) { this.scaledImage = scaledImage; }
    
    public int getScaledImageWidth() { return scaledImageWidth; }
    public void setScaledImageWidth(int width) { this.scaledImageWidth = width; }
    
    public int getMaxImageWidth() { return maxImageWidth; }
    public void setMaxImageWidth(int width) { this.maxImageWidth = width; }
    
    public boolean isFullSizeImage() { return fullSizeImage; }
    public void setFullSizeImage(boolean fullSizeImage) { this.fullSizeImage = fullSizeImage; }
    
    public String getPostFormats() { return postFormats; }
    public void setPostFormats(String postFormats) { this.postFormats = postFormats; }
    
    public boolean isPhotonCapable() { return dotcomFlag; }
    public String getPhotonUrl(String url, int width) { return url; }
    public boolean isFeaturedImageCapable() { return true; }
}
