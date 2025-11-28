package lnreadera._2.mocks;

public class BookmarkModelAdapter {
    private java.util.ArrayList<BookmarkModel> bookmarks;
    
    public BookmarkModelAdapter(SherlockActivity context, int resource, java.util.ArrayList<BookmarkModel> bookmarks) {
        this.bookmarks = bookmarks;
    }
    
    public int getCount() {
        return bookmarks.size();
    }
    
    public BookmarkModel getItem(int position) {
        return bookmarks.get(position);
    }
}
