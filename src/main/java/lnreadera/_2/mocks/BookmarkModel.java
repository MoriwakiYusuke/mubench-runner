package lnreadera._2.mocks;

public class BookmarkModel {
    private int pIndex;
    private String excerpt = "";
    private PageModel page;
    
    public BookmarkModel() {}
    
    public int getpIndex() {
        return pIndex;
    }
    
    public void setpIndex(int pIndex) {
        this.pIndex = pIndex;
    }
    
    public String getExcerpt() {
        return excerpt;
    }
    
    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }
    
    public PageModel getPage() {
        return page;
    }
    
    public void setPage(PageModel page) {
        this.page = page;
    }
}
