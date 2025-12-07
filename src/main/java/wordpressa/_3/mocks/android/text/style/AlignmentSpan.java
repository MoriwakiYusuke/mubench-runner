package wordpressa._3.mocks.android.text.style;

import wordpressa._3.mocks.android.text.Layout;

public interface AlignmentSpan {
    public enum Alignment {
        ALIGN_NORMAL, ALIGN_OPPOSITE, ALIGN_CENTER
    }
    
    Alignment getAlignment();
    
    public static class Standard implements AlignmentSpan {
        private Alignment alignment;
        
        public Standard(Alignment alignment) {
            this.alignment = alignment;
        }
        
        // Layout.Alignmentを受け付けるオーバーロードコンストラクタ
        public Standard(Layout.Alignment layoutAlignment) {
            this.alignment = Alignment.valueOf(layoutAlignment.name());
        }
        
        @Override
        public Alignment getAlignment() {
            return alignment;
        }
    }
}
