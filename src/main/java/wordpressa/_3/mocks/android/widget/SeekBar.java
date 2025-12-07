package wordpressa._3.mocks.android.widget;

import wordpressa._3.mocks.android.view.View;

public class SeekBar extends View {
    private int progress;
    private int max = 100;
    private OnSeekBarChangeListener listener;
    
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    
    public int getMax() { return max; }
    public void setMax(int max) { this.max = max; }
    
    public void setOnSeekBarChangeListener(OnSeekBarChangeListener listener) {
        this.listener = listener;
    }
    
    public interface OnSeekBarChangeListener {
        void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser);
        void onStartTrackingTouch(SeekBar seekBar);
        void onStopTrackingTouch(SeekBar seekBar);
    }
}
