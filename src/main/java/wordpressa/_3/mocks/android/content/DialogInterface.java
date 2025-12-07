package wordpressa._3.mocks.android.content;

public interface DialogInterface {
    int BUTTON_POSITIVE = -1;
    int BUTTON_NEGATIVE = -2;
    int BUTTON_NEUTRAL = -3;
    
    void dismiss();
    
    interface OnClickListener {
        void onClick(DialogInterface dialog, int which);
    }
    
    interface OnDismissListener {
        void onDismiss(DialogInterface dialog);
    }
    
    interface OnCancelListener {
        void onCancel(DialogInterface dialog);
    }
}
