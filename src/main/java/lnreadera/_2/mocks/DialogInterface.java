package lnreadera._2.mocks;

public interface DialogInterface {
    void dismiss();
    void cancel();
    
    interface OnCancelListener {
        void onCancel(DialogInterface dialog);
    }
    
    interface OnClickListener {
        void onClick(DialogInterface dialog, int which);
    }
    
    interface OnDismissListener {
        void onDismiss(DialogInterface dialog);
    }
}
