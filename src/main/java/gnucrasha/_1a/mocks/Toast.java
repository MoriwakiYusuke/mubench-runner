package gnucrasha._1a.mocks;

/**
 * Minimal Toast implementation storing the last shown message id.
 */
public class Toast {

    public static final int LENGTH_SHORT = 0;

    private final Context context;
    private final int messageId;
    private final int duration;

    private static Toast lastToast;

    private Toast(Context context, int messageId, int duration) {
        this.context = context;
        this.messageId = messageId;
        this.duration = duration;
    }

    public static Toast makeText(Context context, int messageId, int duration) {
        Toast toast = new Toast(context, messageId, duration);
        lastToast = toast;
        return toast;
    }

    public void show() {
        // no-op
    }

    public int getMessageId() {
        return messageId;
    }

    public int getDuration() {
        return duration;
    }

    public static Toast getLastToast() {
        return lastToast;
    }
}
