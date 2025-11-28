package lnreadera._1.mocks;

public class NonLeakingWebView {
    public void destroy() {}
    public void setBackgroundColor(int color) {}
    public void loadUrl(String url) {}
    public WebSettings getSettings() { return new WebSettings(); }
}
