package app.services.impl;

import app.model.City;
import app.services.CityService;
import app.services.impl.api.CityServiceApi;

public class CityServiceImpl
        extends AbstractCrudServiceImpl<City>
        implements CityService {

    public CityServiceImpl() {
        super(CityServiceApi.class, City.class, "cities");
    }


    private CityServiceApi getServiceApi() {
        return (CityServiceApi) getCrudServiceApi();
    }

}
