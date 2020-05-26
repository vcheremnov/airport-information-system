package app.services.impl;

import app.model.FlightDelay;
import app.services.FlightDelayService;
import app.services.impl.api.FlightDelayServiceApi;

public class FlightDelayServiceImpl
        extends AbstractCrudServiceImpl<FlightDelay>
        implements FlightDelayService {

    public FlightDelayServiceImpl() {
        super(FlightDelayServiceApi.class, FlightDelay.class, "flight-delays");
    }


    private FlightDelayServiceApi getServiceApi() {
        return (FlightDelayServiceApi) getCrudServiceApi();
    }

}
