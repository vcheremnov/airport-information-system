package app.services.impl.service;

import app.model.City;
import app.model.Ticket;
import app.services.AbstractCrudService;
import app.services.ServiceResponse;
import app.services.impl.api.TicketServiceApi;
import retrofit2.Call;
import retrofit2.http.GET;

import java.util.Map;

public class TicketService extends AbstractCrudService<Ticket> {

    public TicketService() {
        super(TicketServiceApi.class, Ticket.class, "tickets");
    }

    ServiceResponse<Map<City, Double>> getAverageSoldByCity() {
        var call = getServiceApi().getAverageSoldByCity();
        return getServerResponse(call);
    };

    private TicketServiceApi getServiceApi() {
        return (TicketServiceApi) getCrudServiceApi();
    }

}
