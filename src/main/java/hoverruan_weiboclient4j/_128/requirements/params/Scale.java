package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;

public class Scale extends BooleanParam implements
        LocationService.GetMapImageParam {

    public Scale(boolean value) {
        super(value);
    }

    protected String paramKey() {
        return "scale";
    }
}
