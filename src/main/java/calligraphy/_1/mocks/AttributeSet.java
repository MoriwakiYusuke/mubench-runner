package calligraphy._1.mocks;

/**
 * Simplified AttributeSet that returns defaults.
 */
public class AttributeSet {

    public int getAttributeResourceValue(String namespace, String attribute, int defaultValue) {
        return defaultValue;
    }

    public String getAttributeValue(String namespace, String attribute) {
        return null;
    }
}
