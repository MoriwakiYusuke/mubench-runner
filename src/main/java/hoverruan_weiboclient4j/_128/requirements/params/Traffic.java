package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;

public class Traffic extends BooleanParam implements
        LocationService.GetMapImageParam {

    public Traffic(boolean value) {
        super(value);
    }

    protected String paramKey() {
        return "traffic";
    }
}
