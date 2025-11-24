package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class PostCode extends StringParam implements
        PlaceService.CreatePoiParam {

    public PostCode(String value) {
        super(value);
    }

    protected String paramKey() {
        return "postcode";
    }
}
