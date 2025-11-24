package hoverruan_weiboclient4j._128.params;

import weiboclient4j.PlaceService;

/**
 * @author Hover Ruan
 */
public enum RelationshipFilter implements
        PlaceService.GetNearbyUsersListParam {
    All(0), StrangerOnly(1), Following(2);

    private int value;

    RelationshipFilter(int value) {
        this.value = value;
    }

    public void addParameter(Parameters params) {
        params.add("filter", value);
    }
}
