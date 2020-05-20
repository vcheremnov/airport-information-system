package app.services.impl.service;

import app.model.AirplaneType;
import app.services.AbstractCrudService;
import app.services.impl.api.AirplaneTypeServiceApi;

public class AirplaneTypeService extends AbstractCrudService<AirplaneType> {

    public AirplaneTypeService() {
        super(AirplaneTypeServiceApi.class, AirplaneType.class, "airplane-types");
    }


    private AirplaneTypeServiceApi getServiceApi() {
        return (AirplaneTypeServiceApi) getCrudServiceApi();
    }

}
