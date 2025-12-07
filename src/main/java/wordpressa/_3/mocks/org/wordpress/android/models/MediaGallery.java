package wordpressa._3.mocks.org.wordpress.android.models;

import java.io.Serializable;
import java.util.ArrayList;

public class MediaGallery implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long uniqueId;
    private ArrayList<String> ids = new ArrayList<>();
    private boolean isRandom;
    private int numColumns = 3;
    private String type = "rectangular";
    
    public long getUniqueId() { return uniqueId; }
    public void setUniqueId(long id) { this.uniqueId = id; }
    
    public ArrayList<String> getIds() { return ids; }
    public void setIds(ArrayList<String> ids) { this.ids = ids; }
    
    public boolean isRandom() { return isRandom; }
    public void setRandom(boolean random) { this.isRandom = random; }
    
    public int getNumColumns() { return numColumns; }
    public void setNumColumns(int numColumns) { this.numColumns = numColumns; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}
