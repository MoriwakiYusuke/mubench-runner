package wordpressa._3.mocks.android.app;

import wordpressa._3.mocks.android.net.Uri;
import wordpressa._3.mocks.android.os.Bundle;
import java.io.Serializable;
import java.util.ArrayList;

public class Intent {
    public static final String ACTION_VIEW = "android.intent.action.VIEW";
    public static final String ACTION_SEND = "android.intent.action.SEND";
    public static final String ACTION_SEND_MULTIPLE = "android.intent.action.SEND_MULTIPLE";
    public static final String ACTION_PICK = "android.intent.action.PICK";
    public static final String ACTION_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED";
    public static final String EXTRA_STREAM = "android.intent.extra.STREAM";
    public static final String EXTRA_TEXT = "android.intent.extra.TEXT";
    public static final String EXTRA_SUBJECT = "android.intent.extra.SUBJECT";
    
    private Uri data;
    private String action;
    private String type;
    private Bundle extras = new Bundle();
    
    public Intent() {}
    public Intent(String action) { this.action = action; }
    public Intent(String action, Uri data) { this.action = action; this.data = data; }
    public Intent(Object context, Class<?> cls) { /* Context, Class constructor */ }
    
    public Intent setData(Uri data) { this.data = data; return this; }
    public Uri getData() { return data; }
    public Intent setAction(String action) { this.action = action; return this; }
    public String getAction() { return action; }
    public Intent setType(String type) { this.type = type; return this; }
    public String getType() { return type; }
    public Intent putExtra(String name, Object value) { extras.putString(name, value != null ? value.toString() : null); return this; }
    public Intent putExtra(String name, int value) { extras.putInt(name, value); return this; }
    public Intent putExtra(String name, boolean value) { extras.putBoolean(name, value); return this; }
    public Intent putExtra(String name, String value) { extras.putString(name, value); return this; }
    public Intent putExtra(String name, Serializable value) { return this; }
    public int getIntExtra(String name, int defaultValue) { return extras.getInt(name, defaultValue); }
    public boolean getBooleanExtra(String name, boolean defaultValue) { return extras.getBoolean(name, defaultValue); }
    public String getStringExtra(String name) { return extras.getString(name); }
    public Object getParcelableExtra(String name) { return extras.getParcelable(name); }
    public Serializable getSerializableExtra(String name) { return null; }
    public <T> ArrayList<T> getParcelableArrayListExtra(String name) { return new ArrayList<>(); }
    public ArrayList<String> getStringArrayListExtra(String name) { return new ArrayList<>(); }
    public boolean hasExtra(String name) { return extras.containsKey(name); }
    public Bundle getExtras() { return extras; }
}
