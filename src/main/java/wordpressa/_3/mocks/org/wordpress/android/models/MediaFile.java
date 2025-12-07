package wordpressa._3.mocks.org.wordpress.android.models;

public class MediaFile {
    private int id;
    private String postId;
    private String blogId;
    private String filePath;
    private String fileName;
    private String title;
    private String description;
    private String caption;
    private String fileUrl;
    private String thumbnailUrl;
    private String mimeType;
    private boolean featured;
    private int width;
    private int height;
    private boolean video;
    private long dateCreatedGmt;
    private String uploadState;
    private String mediaId;
    
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getPostId() { return postId; }
    public void setPostId(String postId) { this.postId = postId; }
    public void setPostID(long id) { this.postId = String.valueOf(id); }
    
    public String getBlogId() { return blogId; }
    public void setBlogId(String blogId) { this.blogId = blogId; }
    
    public String getFilePath() { return filePath; }
    public void setFilePath(String filePath) { this.filePath = filePath; }
    
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getCaption() { return caption; }
    public void setCaption(String caption) { this.caption = caption; }
    
    public String getFileURL() { return fileUrl; }
    public void setFileURL(String fileUrl) { this.fileUrl = fileUrl; }
    
    public String getThumbnailURL() { return thumbnailUrl; }
    public void setThumbnailURL(String thumbnailUrl) { this.thumbnailUrl = thumbnailUrl; }
    
    public String getMimeType() { return mimeType; }
    public void setMimeType(String mimeType) { this.mimeType = mimeType; }
    
    public boolean isFeatured() { return featured; }
    public void setFeatured(boolean featured) { this.featured = featured; }
    
    public int getWidth() { return width; }
    public void setWidth(int width) { this.width = width; }
    
    public int getHeight() { return height; }
    public void setHeight(int height) { this.height = height; }
    
    public boolean isVideo() { return video; }
    public void setVideo(boolean video) { this.video = video; }
    
    public long getDateCreatedGmt() { return dateCreatedGmt; }
    public void setDateCreatedGmt(long dateCreatedGmt) { this.dateCreatedGmt = dateCreatedGmt; }
    
    public String getUploadState() { return uploadState; }
    public void setUploadState(String uploadState) { this.uploadState = uploadState; }
    
    public String getMediaId() { return mediaId; }
    public void setMediaId(String mediaId) { this.mediaId = mediaId; }
    
    private boolean featuredInPost;
    private int horizontalAlignment;
    
    public boolean isFeaturedInPost() { return featuredInPost; }
    public void setFeaturedInPost(boolean featuredInPost) { this.featuredInPost = featuredInPost; }
    
    public int getHorizontalAlignment() { return horizontalAlignment; }
    public void setHorizontalAlignment(int horizontalAlignment) { this.horizontalAlignment = horizontalAlignment; }
    
    public void save() {}
}
