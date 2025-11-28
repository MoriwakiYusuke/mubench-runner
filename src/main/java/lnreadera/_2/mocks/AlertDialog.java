package lnreadera._2.mocks;

public class AlertDialog {
    private String title;
    private String message;
    
    public void show() {}
    public void dismiss() {}
    
    public static class Builder {
        private SherlockActivity context;
        private String title;
        private String message;
        
        public Builder(SherlockActivity context) {
            this.context = context;
        }
        
        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }
        
        public Builder setTitle(int resId) {
            return this;
        }
        
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        
        public Builder setMessage(int resId) {
            return this;
        }
        
        public Builder setPositiveButton(String text, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setPositiveButton(int resId, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setNegativeButton(String text, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setNegativeButton(int resId, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setNeutralButton(String text, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setNeutralButton(int resId, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setOnCancelListener(DialogInterface.OnCancelListener listener) {
            return this;
        }
        
        public Builder setView(View view) {
            return this;
        }
        
        public AlertDialog create() {
            return new AlertDialog();
        }
        
        public AlertDialog show() {
            return new AlertDialog();
        }
        
        public Builder setItems(String[] items, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setSingleChoiceItems(String[] items, int checkedItem, DialogInterface.OnClickListener listener) {
            return this;
        }
        
        public Builder setAdapter(Object adapter, DialogInterface.OnClickListener listener) {
            return this;
        }
    }
}
