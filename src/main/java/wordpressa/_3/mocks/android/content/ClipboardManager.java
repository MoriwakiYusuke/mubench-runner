package wordpressa._3.mocks.android.content;

public class ClipboardManager {
    private ClipData primaryClip;
    
    public void setPrimaryClip(ClipData clip) { this.primaryClip = clip; }
    public ClipData getPrimaryClip() { return primaryClip; }
    public boolean hasPrimaryClip() { return primaryClip != null; }
    public CharSequence getText() { 
        if (primaryClip != null && primaryClip.getItemCount() > 0) {
            return primaryClip.getItemAt(0).getText();
        }
        return null;
    }
}
