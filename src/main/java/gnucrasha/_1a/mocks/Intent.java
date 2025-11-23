package gnucrasha._1a.mocks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Simplified Intent capturing action, extras, flags, and categories.
 */
public class Intent {

    public static final String ACTION_MAIN = "android.intent.action.MAIN";
    public static final String CATEGORY_HOME = "android.intent.category.HOME";
    public static final int FLAG_ACTIVITY_CLEAR_TOP = 0x04000000;
    public static final int FLAG_ACTIVITY_NEW_TASK = 0x10000000;

    private String action;
    private final Map<String, Object> extras = new HashMap<>();
    private int flags;
    private final Set<String> categories = new HashSet<>();
    private String className;

    private Uri data;
    private String type;

    public Intent() {
    }

    public Intent(Context context, Class<?> clazz) {
        setClassName(context, clazz.getName());
    }

    public Intent setAction(String action) {
        this.action = action;
        return this;
    }

    public String getAction() {
        return action;
    }

    public Intent putExtra(String key, String value) {
        extras.put(key, value);
        return this;
    }

    public Intent putExtra(String key, long value) {
        extras.put(key, value);
        return this;
    }

    public String getStringExtra(String key) {
        Object value = extras.get(key);
        return value instanceof String ? (String) value : null;
    }

    public long getLongExtra(String key, long defaultValue) {
        Object value = extras.get(key);
        return value instanceof Number ? ((Number) value).longValue() : defaultValue;
    }

    public Intent setFlags(int flags) {
        this.flags = flags;
        return this;
    }

    public int getFlags() {
        return flags;
    }

    public Intent addCategory(String category) {
        categories.add(category);
        return this;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public Intent setClassName(Context context, String className) {
        this.className = className;
        return this;
    }

    public String getClassName() {
        return className;
    }

    public Intent setDataAndType(Uri data, String type) {
        this.data = data;
        this.type = type;
        return this;
    }

    public Uri getData() {
        return data;
    }

    public String getType() {
        return type;
    }
}
