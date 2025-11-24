package hoverruan_weiboclient4j._128.params;

import weiboclient4j.StatusService;

/**
 * @author Hover Ruan
 */
public class Latitude extends FloatParam implements StatusService.UploadImageUrlParam {
    public Latitude(float value) {
        super(value);
    }

    protected String paramKey() {
        return "lat";
    }
}
