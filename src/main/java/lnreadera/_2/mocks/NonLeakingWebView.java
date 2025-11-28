package lnreadera._2.mocks;

public class NonLeakingWebView extends View {
    public void destroy() {}
    public void setBackgroundColor(int color) {}
    public void loadUrl(String url) {}
    public WebSettings getSettings() { return new WebSettings(); }
    public void removeAllViews() {}
}
