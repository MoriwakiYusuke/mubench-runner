package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class Pid extends StringParam implements
        PlaceService.GetPoiCategoriesParam {
    public Pid(String value) {
        super(value);
    }

    protected String paramKey() {
        return "pid";
    }
}
