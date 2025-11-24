package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class Extra extends StringParam implements
        PlaceService.CreatePoiParam {

    public Extra(String value) {
        super(value);
    }

    protected String paramKey() {
        return "extra";
    }
}
