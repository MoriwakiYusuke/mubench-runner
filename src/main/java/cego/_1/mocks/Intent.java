package cego._1.mocks;

/**
 * Simplified intent used to capture action, data, and type.
 */
public class Intent {

    public static final String ACTION_VIEW = "android.intent.action.VIEW";

    private String action;
    private Uri data;
    private String type;

    public void setAction(String action) {
        this.action = action;
    }

    public void setDataAndType(Uri data, String type) {
        this.data = data;
        this.type = type;
    }

    public String getAction() {
        return action;
    }

    public Uri getData() {
        return data;
    }

    public String getType() {
        return type;
    }
}
