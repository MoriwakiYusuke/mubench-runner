package hoverruan_weiboclient4j._128.params;

import hoverruan_weiboclient4j._128.requirements.utils.StringUtils;

public class PicId extends StringParam {
    public PicId(String... picIdList) {
        super(StringUtils.join(picIdList, ","));
    }

    protected String paramKey() {
        return "pic_id";
    }
}
