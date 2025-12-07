package wordpressa._3.mocks.android.content;

public class ClipData {
    private Item item;
    
    public int getItemCount() { return item != null ? 1 : 0; }
    public Item getItemAt(int index) { return item; }
    
    public static ClipData newPlainText(CharSequence label, CharSequence text) {
        ClipData clip = new ClipData();
        clip.item = new Item(text);
        return clip;
    }
    
    public static class Item {
        private CharSequence text;
        public Item(CharSequence text) { this.text = text; }
        public CharSequence getText() { return text; }
    }
}
