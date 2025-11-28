package lnreadera._2.mocks;

import java.util.ArrayList;

public class NovelCollectionModel {
    private PageModel pageModel;
    private ArrayList<BookModel> bookModelList = new ArrayList<>();
    
    public NovelCollectionModel() {}
    
    public PageModel getPageModel() {
        return pageModel;
    }
    
    public void setPageModel(PageModel pageModel) {
        this.pageModel = pageModel;
    }
    
    public ArrayList<BookModel> getBookModelList() {
        return bookModelList;
    }
    
    public void setBookModelList(ArrayList<BookModel> bookModelList) {
        this.bookModelList = bookModelList;
    }
}
