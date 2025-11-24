package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;
import static hoverruan_weiboclient4j._128.requirements.utils.StringUtils.join;

public class Names extends StringParam implements
        LocationService.GetMapImageParam {

    public Names(String... value) {
        super(join(value, ","));
    }

    protected String paramKey() {
        return "names";
    }
}
