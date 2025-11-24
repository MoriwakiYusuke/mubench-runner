package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;

public class Font extends StringParam implements
        LocationService.GetMapImageParam {

    public Font(String value) {
        super(value);
    }

    protected String paramKey() {
        return "font";
    }
}
