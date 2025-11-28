package lnreadera._2.mocks;

public class PageModel {
    private String page = "";
    private int lastUpdate = 0;
    private String title = "";
    
    public PageModel() {}
    
    public String getPage() {
        return page;
    }
    
    public void setPage(String page) {
        this.page = page;
    }
    
    public int getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(int lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
}
