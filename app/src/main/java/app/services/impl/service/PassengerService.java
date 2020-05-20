package app.services.impl.service;

import app.model.Passenger;
import app.services.AbstractCrudService;
import app.services.impl.api.PassengerServiceApi;

public class PassengerService extends AbstractCrudService<Passenger> {

    public PassengerService() {
        super(PassengerServiceApi.class, Passenger.class, "passengers");
    }


    private PassengerServiceApi getServiceApi() {
        return (PassengerServiceApi) getCrudServiceApi();
    }

}
