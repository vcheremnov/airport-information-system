package app.services.impl.service;

import app.model.City;
import app.services.AbstractCrudService;
import app.services.impl.api.CityServiceApi;

public class CityService extends AbstractCrudService<City> {

    public CityService() {
        super(CityServiceApi.class, City.class, "cities");
    }


    private CityServiceApi getServiceApi() {
        return (CityServiceApi) getCrudServiceApi();
    }

}
