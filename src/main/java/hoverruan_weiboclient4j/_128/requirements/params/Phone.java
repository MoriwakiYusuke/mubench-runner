package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class Phone extends StringParam implements
        PlaceService.CreatePoiParam {

    public Phone(String value) {
        super(value);
    }

    protected String paramKey() {
        return "phone";
    }
}
