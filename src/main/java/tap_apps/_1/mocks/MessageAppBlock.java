package tap_apps._1.mocks;

/**
 * Mock for org.jSyncManager.Conduit.NSMobileMessenger.DataTypes.MessageAppBlock
 */
public class MessageAppBlock extends DLPRecord {
    
    public static final int CATEGORY_IN = 0;
    public static final int CATEGORY_OUT = 1;
    public static final int CATEGORY_DELETED = 2;
    
    private static final String DATABASE_NAME = "NSMobileMsg";
    
    private long lastSyncDateTime;
    private CategoryInfo[] categories = new CategoryInfo[16];
    
    public MessageAppBlock() {
        for (int i = 0; i < 16; i++) {
            categories[i] = new CategoryInfo();
        }
    }
    
    public MessageAppBlock(byte[] data) throws ParseException {
        this();
        // Parse from byte array (mock)
    }
    
    public static String getDatabaseName() {
        return DATABASE_NAME;
    }
    
    public long getLastSyncDateTime() {
        return lastSyncDateTime;
    }
    
    public void setLastSyncDateTime(long time) {
        this.lastSyncDateTime = time;
    }
    
    public void setCategoryInfo(CategoryInfo info, int index) {
        if (index >= 0 && index < 16) {
            categories[index] = info;
        }
    }
    
    public CategoryInfo getCategoryInfo(int index) {
        if (index >= 0 && index < 16) {
            return categories[index];
        }
        return null;
    }
}
