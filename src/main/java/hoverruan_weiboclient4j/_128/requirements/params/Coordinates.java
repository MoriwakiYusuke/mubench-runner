package hoverruan_weiboclient4j._128.params;

import weiboclient4j.LocationService;
import static hoverruan_weiboclient4j._128.requirements.utils.StringUtils.join;

public class Coordinates extends StringParam implements
        LocationService.GetMapImageParam,
        LocationService.SearchByAreaParam {

    public Coordinates(String value) {
        super(value);
    }

    public Coordinates(Coordinate... coordinates) {
        this(join(coordinates, "|"));
    }

    protected String paramKey() {
        return "coordinates";
    }
}
