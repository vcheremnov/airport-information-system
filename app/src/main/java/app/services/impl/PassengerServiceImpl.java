package app.services.impl;

import app.model.Passenger;
import app.services.PassengerService;
import app.services.impl.api.PassengerServiceApi;

public class PassengerServiceImpl
        extends AbstractCrudServiceImpl<Passenger>
        implements PassengerService {

    public PassengerServiceImpl(String baseUrl) {
        super(PassengerServiceApi.class, Passenger.class, baseUrl, "passengers");
    }


    private PassengerServiceApi getServiceApi() {
        return (PassengerServiceApi) getCrudServiceApi();
    }

}
