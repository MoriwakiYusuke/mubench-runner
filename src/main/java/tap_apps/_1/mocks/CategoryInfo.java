package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.API.Protocol.Util.StdApps.CategoryInfo
 */
public class CategoryInfo {
    
    private int categoryID;
    private String categoryName;
    private boolean modifiedFlag;
    
    public CategoryInfo() {
    }
    
    public int getCategoryID() {
        return categoryID;
    }
    
    public void setCategoryID(int id) {
        this.categoryID = id;
    }
    
    public String getCategoryName() {
        return categoryName;
    }
    
    public void setCategoryName(String name) {
        this.categoryName = name;
    }
    
    public boolean getModifiedFlag() {
        return modifiedFlag;
    }
    
    public void setModifiedFlag(boolean flag) {
        this.modifiedFlag = flag;
    }
}
