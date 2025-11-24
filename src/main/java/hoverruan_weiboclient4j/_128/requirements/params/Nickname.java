package hoverruan_weiboclient4j._128.params;

/**
 * @author Hover Ruan
 */
public class Nickname extends StringParam {
    public Nickname(String value) {
        super(value);
    }

    protected String paramKey() {
        return "nickname";
    }
}
