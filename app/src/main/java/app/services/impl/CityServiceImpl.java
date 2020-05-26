package app.services.impl;

import app.model.City;
import app.services.CityService;
import app.services.impl.api.CityServiceApi;

public class CityServiceImpl
        extends AbstractCrudServiceImpl<City>
        implements CityService {

    public CityServiceImpl(String baseUrl) {
        super(CityServiceApi.class, City.class, baseUrl, "cities");
    }


    private CityServiceApi getServiceApi() {
        return (CityServiceApi) getCrudServiceApi();
    }

}
