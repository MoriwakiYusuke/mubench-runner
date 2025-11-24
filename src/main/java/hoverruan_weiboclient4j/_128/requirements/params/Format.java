package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;

public class Format extends StringParam implements
        LocationService.GetMapImageParam {

    public Format(String value) {
        super(value);
    }

    protected String paramKey() {
        return "format";
    }
}
