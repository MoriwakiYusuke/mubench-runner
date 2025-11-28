package lnreadera._2.mocks;

public class NovelsDao {
    private static NovelsDao instance;
    
    public static NovelsDao getInstance() {
        if (instance == null) {
            instance = new NovelsDao();
        }
        return instance;
    }
    
    public PageModel getPageModel(String page, Object callback) {
        return new PageModel();
    }
    
    public NovelContentModel getNovelContent(PageModel page, boolean forceRefresh, Object callback) {
        return new NovelContentModel();
    }
    
    public void updatePageModel(PageModel page) {}
    
    public java.util.ArrayList<BookmarkModel> getBookmarks(PageModel page) {
        return new java.util.ArrayList<>();
    }
    
    public void deleteBookmark(BookmarkModel bookmark) {}
    
    public void insertBookmark(BookmarkModel bookmark) {}
    
    public NovelCollectionModel getNovelDetails(PageModel page, Object callback) {
        return new NovelCollectionModel();
    }
}
