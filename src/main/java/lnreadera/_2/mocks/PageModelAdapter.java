package lnreadera._2.mocks;

public class PageModelAdapter {
    private java.util.ArrayList<PageModel> pages;
    
    public PageModelAdapter(SherlockActivity context, int resource, java.util.ArrayList<PageModel> pages) {
        this.pages = pages;
    }
    
    public int getCount() {
        return pages.size();
    }
    
    public PageModel getItem(int position) {
        return pages.get(position);
    }
}
