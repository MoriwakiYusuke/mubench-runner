package weiboclient4j;

import hoverruan_weiboclient4j._128.params.AddressCode;
import hoverruan_weiboclient4j._128.params.CapitalLetter;
import hoverruan_weiboclient4j._128.params.Country;
import hoverruan_weiboclient4j._128.params.Language;
import hoverruan_weiboclient4j._128.params.Province;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Hover Ruan
 */
public class CommonService extends AbstractService {
    public CommonService(WeiboClient client) {
        super(client);
    }

    public Map<String, String> getTimezoneMap() throws WeiboClientException {
        return getTimezoneMap(null);
    }

    @SuppressWarnings("unchecked")
    public Map<String, String> getTimezoneMap(Language language) throws WeiboClientException {
        return doGet("common/get_timezone",
                withParams(language), HashMap.class);
    }

    public Map<String, String> getCountryMap() throws WeiboClientException {
        return getCountryMap(null, null);
    }

    public Map<String, String> getCountryMap(Language language, CapitalLetter capitalLetter)
            throws WeiboClientException {
        List<Map<String, String>> response = doGet("common/get_country",
                withParams(language, capitalLetter), LIST_MAP_S_S_TYPE_REFERENCE);

        return mergeSingleItemMap(response);
    }

    public Map<String, String> getProvinceMap(Country country) throws WeiboClientException {
        return getProvinceMap(country, null, null);
    }

    public Map<String, String> getProvinceMap(Country country, Language language, CapitalLetter capitalLetter)
            throws WeiboClientException {
        List<Map<String, String>> response = doGet("common/get_province",
                withParams(country, language, capitalLetter), LIST_MAP_S_S_TYPE_REFERENCE);

        return mergeSingleItemMap(response);
    }

    public Map<String, String> getCityMap(Province province) throws WeiboClientException {
        return getCityMap(province, null, null);
    }

    public Map<String, String> getCityMap(Province province, Language language, CapitalLetter capitalLetter)
            throws WeiboClientException {
        List<Map<String, String>> response = doGet("common/get_city",
                withParams(province, language, capitalLetter), LIST_MAP_S_S_TYPE_REFERENCE);

        return mergeSingleItemMap(response);
    }

    public Map<String, String> codeToLocation(Collection<AddressCode> codes) throws WeiboClientException {
        List<Map<String, String>> response = doGet("common/code_to_location",
                withParams(AddressCode.codesParam(codes)), LIST_MAP_S_S_TYPE_REFERENCE);

        return mergeSingleItemMap(response);
    }
}
