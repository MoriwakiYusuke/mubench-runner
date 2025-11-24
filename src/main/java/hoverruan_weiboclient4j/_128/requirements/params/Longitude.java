package hoverruan_weiboclient4j._128.params;

import weiboclient4j.StatusService;

/**
 * @author Hover Ruan
 */
public class Longitude extends FloatParam implements StatusService.UploadImageUrlParam {
    public Longitude(float value) {
        super(value);
    }

    protected String paramKey() {
        return "long";
    }
}
