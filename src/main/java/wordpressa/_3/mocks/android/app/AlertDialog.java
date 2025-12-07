package wordpressa._3.mocks.android.app;

import wordpressa._3.mocks.android.content.Context;
import wordpressa._3.mocks.android.content.DialogInterface;
import wordpressa._3.mocks.android.view.View;
import wordpressa._3.mocks.android.view.Window;

public class AlertDialog implements DialogInterface {
    
    public void show() {}
    public void dismiss() {}
    public Window getWindow() { return new Window(); }
    
    public static class Builder {
        public Builder(Context context) {}
        public Builder setTitle(CharSequence title) { return this; }
        public Builder setTitle(int resId) { return this; }
        public Builder setMessage(CharSequence message) { return this; }
        public Builder setMessage(int resId) { return this; }
        public Builder setPositiveButton(int resId, DialogInterface.OnClickListener listener) { return this; }
        public Builder setPositiveButton(CharSequence text, DialogInterface.OnClickListener listener) { return this; }
        public Builder setNegativeButton(int resId, DialogInterface.OnClickListener listener) { return this; }
        public Builder setNegativeButton(CharSequence text, DialogInterface.OnClickListener listener) { return this; }
        public Builder setNeutralButton(int resId, DialogInterface.OnClickListener listener) { return this; }
        public Builder setView(View view) { return this; }
        public Builder setItems(CharSequence[] items, DialogInterface.OnClickListener listener) { return this; }
        public Builder setCancelable(boolean cancelable) { return this; }
        public AlertDialog create() { return new AlertDialog(); }
        public AlertDialog show() { return create(); }
    }
}
