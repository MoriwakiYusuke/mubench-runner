package wordpressa._3.mocks.android.text;

// Layout.Alignment はコードで使用されるが、AlignmentSpan.Alignmentと互換性が必要
// 元のAndroid SDKでは同じ型なので、ここでも同じにする
public class Layout {
    // AlignmentSpan.Alignment を参照するためのエイリアス
    // コード内での Layout.Alignment.ALIGN_NORMAL は AlignmentSpan.Alignment.ALIGN_NORMAL と同等
    public static enum Alignment {
        ALIGN_NORMAL, ALIGN_OPPOSITE, ALIGN_CENTER;
        
        public wordpressa._3.mocks.android.text.style.AlignmentSpan.Alignment toAlignmentSpanAlignment() {
            return wordpressa._3.mocks.android.text.style.AlignmentSpan.Alignment.valueOf(this.name());
        }
    }
    
    public int getLineForOffset(int offset) { return 0; }
    public int getLineStart(int line) { return 0; }
    public int getLineEnd(int line) { return 0; }
    public float getLineLeft(int line) { return 0f; }
    public float getLineRight(int line) { return 0f; }
    public int getLineTop(int line) { return 0; }
    public int getLineBottom(int line) { return 0; }
    public int getLineCount() { return 1; }
    public int getOffsetForHorizontal(int line, float horiz) { return 0; }
    public int getLineForVertical(int vertical) { return 0; }
    public int getHeight() { return 0; }
    public int getWidth() { return 0; }
}
