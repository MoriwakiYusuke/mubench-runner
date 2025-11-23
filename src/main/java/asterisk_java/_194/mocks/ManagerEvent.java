package asterisk_java._194.mocks;

/**
 * Minimal stub mirroring the original ManagerEvent hierarchy.
 */
public abstract class ManagerEvent {

    private final Object source;

    protected ManagerEvent(Object source) {
        this.source = source;
    }

    public Object getSource() {
        return source;
    }
}
