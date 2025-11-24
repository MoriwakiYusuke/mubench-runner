package hoverruan_weiboclient4j._128.params;

/**
 * @author Hover Ruan
 */
public abstract class LongParam implements ParameterAction {
    private long value;

    public LongParam(long value) {
        this.value = value;
    }

    public long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    public boolean isValid() {
        return value > 0;
    }

    protected abstract String paramKey();

    public void addParameter(Parameters params) {
        if (isValid()) {
            params.add(paramKey(), getValue());
        }
    }
}
