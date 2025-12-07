package wordpressa._3.mocks.org.wordpress.android.models;

import java.io.Serializable;

public class Post implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public static final String QUICK_MEDIA_TYPE_PHOTO = "photo";
    public static final String QUICK_MEDIA_TYPE_VIDEO = "video";
    
    private long localTablePostId;
    private long localTableBlogId;
    private String title = "";
    private String content = "";
    private String description = "";
    private String moreText = "";
    private String status = "draft";
    private String password = "";
    private String postFormat = "";
    private boolean isPage = false;
    private boolean isLocalDraft = true;
    private boolean isUploaded = false;
    private double latitude = 0.0;
    private double longitude = 0.0;
    private boolean isLocalChange = true;
    private String mediaPaths = "";
    private String quickPostType = "";
    private String dateCreated = "";
    private String dateCreatedGmt = "";
    private int mt_keywords = 0;
    
    public Post() {}
    
    public Post(long localTableBlogId, boolean isPage) {
        this.localTableBlogId = localTableBlogId;
        this.isPage = isPage;
    }
    
    public long getLocalTablePostId() { return localTablePostId; }
    public void setLocalTablePostId(long id) { this.localTablePostId = id; }
    
    public long getLocalTableBlogId() { return localTableBlogId; }
    public void setLocalTableBlogId(long id) { this.localTableBlogId = id; }
    
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    
    public String getMoreText() { return moreText; }
    public void setMoreText(String moreText) { this.moreText = moreText; }
    
    public String getPostStatus() { return status; }
    public void setPostStatus(String status) { this.status = status; }
    
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    
    public String getPostFormat() { return postFormat; }
    public void setPostFormat(String format) { this.postFormat = format; }
    
    public boolean isPage() { return isPage; }
    public void setIsPage(boolean isPage) { this.isPage = isPage; }
    
    public boolean isLocalDraft() { return isLocalDraft; }
    public void setLocalDraft(boolean isLocalDraft) { this.isLocalDraft = isLocalDraft; }
    
    public boolean isUploaded() { return isUploaded; }
    public void setUploaded(boolean isUploaded) { this.isUploaded = isUploaded; }
    
    public double getLatitude() { return latitude; }
    public void setLatitude(double latitude) { this.latitude = latitude; }
    
    public double getLongitude() { return longitude; }
    public void setLongitude(double longitude) { this.longitude = longitude; }
    
    public void delete() {}
    
    public void setMt_text_more(String more) { this.moreText = more; }
    
    public boolean isLocalChange() { return isLocalChange; }
    public void setLocalChange(boolean isLocalChange) { this.isLocalChange = isLocalChange; }
    
    public String getMediaPaths() { return mediaPaths; }
    public void setMediaPaths(String paths) { this.mediaPaths = paths; }
    
    public boolean supportsLocation() { return true; }
    public boolean hasLocation() { return latitude != 0.0 || longitude != 0.0; }
    
    public String getQuickPostType() { return quickPostType; }
    public void setQuickPostType(String type) { this.quickPostType = type; }
    
    public String getDateCreated() { return dateCreated; }
    public void setDateCreated(String date) { this.dateCreated = date; }
    
    public String getDateCreatedGmt() { return dateCreatedGmt; }
    public void setDateCreatedGmt(String date) { this.dateCreatedGmt = date; }
    
    public int getMt_keywords() { return mt_keywords; }
    public void setMt_keywords(int keywords) { this.mt_keywords = keywords; }
    
    public long getId() { return localTablePostId; }
    
    public void update() {}
}
