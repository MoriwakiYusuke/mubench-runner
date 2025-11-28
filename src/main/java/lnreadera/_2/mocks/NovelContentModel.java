package lnreadera._2.mocks;

public class NovelContentModel {
    private String content = "";
    private PageModel pageModel;
    private int lastXScroll = 0;
    private int lastYScroll = 0;
    private int lastZoom = 100;
    
    public NovelContentModel() {}
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public PageModel getPageModel() {
        return pageModel;
    }
    
    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
    
    public int getLastXScroll() {
        return lastXScroll;
    }
    
    public void setLastXScroll(int lastXScroll) {
        this.lastXScroll = lastXScroll;
    }
    
    public int getLastYScroll() {
        return lastYScroll;
    }
    
    public void setLastYScroll(int lastYScroll) {
        this.lastYScroll = lastYScroll;
    }
    
    public int getLastZoom() {
        return lastZoom;
    }
    
    public void setLastZoom(int lastZoom) {
        this.lastZoom = lastZoom;
    }
}
