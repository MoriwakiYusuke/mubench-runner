package hoverruan_weiboclient4j._128.params;

import hoverruan_weiboclient4j._128.requirements.utils.StringUtils;

/**
 * @author Hover Ruan
 */
public abstract class StringParam implements ParameterAction {
    private String value;

    public StringParam(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    public boolean isValid() {
        return StringUtils.isNotBlank(value);
    }

    protected abstract String paramKey();

    public void addParameter(Parameters params) {
        if (isValid()) {
            params.add(paramKey(), getValue());
        }
    }
}
