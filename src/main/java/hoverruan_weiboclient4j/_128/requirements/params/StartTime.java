package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public class StartTime extends LongParam implements
        PlaceService.GetNearbyUsersParam,
        PlaceService.GetNearbyPhotosParam,
        PlaceService.GetNearbyTimelineParam {

    public StartTime(long value) {
        super(value);
    }

    protected String paramKey() {
        return "starttime";
    }
}
