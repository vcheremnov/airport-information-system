package app.services.impl;

import app.model.AirplaneType;
import app.services.AirplaneTypeService;
import app.services.impl.api.AirplaneTypeServiceApi;

public class AirplaneTypeServiceImpl
        extends AbstractCrudServiceImpl<AirplaneType>
        implements AirplaneTypeService {

    public AirplaneTypeServiceImpl(String baseUrl) {
        super(AirplaneTypeServiceApi.class, AirplaneType.class, baseUrl, "airplane-types");
    }


    private AirplaneTypeServiceApi getServiceApi() {
        return (AirplaneTypeServiceApi) getCrudServiceApi();
    }

}
