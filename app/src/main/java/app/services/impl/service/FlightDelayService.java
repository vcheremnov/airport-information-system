package app.services.impl.service;

import app.model.FlightDelay;
import app.services.AbstractCrudService;
import app.services.impl.api.FlightDelayServiceApi;

public class FlightDelayService extends AbstractCrudService<FlightDelay> {

    public FlightDelayService() {
        super(FlightDelayServiceApi.class, FlightDelay.class, "flight-delays");
    }


    private FlightDelayServiceApi getServiceApi() {
        return (FlightDelayServiceApi) getCrudServiceApi();
    }

}
