package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;

public class Lines extends StringParam implements
        LocationService.GetMapImageParam {

    public Lines(String value) {
        super(value);
    }

    protected String paramKey() {
        return "lines";
    }
}
