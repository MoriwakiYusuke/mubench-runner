package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class StartBirth extends LongParam implements
        PlaceService.GetNearbyUsersListParam {
    public StartBirth(long value) {
        super(value);
    }

    protected String paramKey() {
        return "startbirth";
    }
}
