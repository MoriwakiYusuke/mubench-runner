package hoverruan_weiboclient4j._128.params;

/**
 * @author Hover Ruan
 */
public class TargetScreenName extends StringParam {
    public static final TargetScreenName EMPTY = new TargetScreenName(null);

    public TargetScreenName(String value) {
        super(value);
    }

    protected String paramKey() {
        return "target_screen_name";
    }
}
