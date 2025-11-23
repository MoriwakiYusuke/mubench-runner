package asterisk_java._81.mocks;

/**
 * Minimal stub of the original ResponseEvent to satisfy compilation.
 */
public abstract class ResponseEvent {

    private final Object source;

    protected ResponseEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
