package app.services.impl;

import app.model.Ticket;
import app.services.ServiceResponse;
import app.services.TicketService;
import app.services.impl.api.TicketServiceApi;

public class TicketServiceImpl
        extends AbstractCrudServiceImpl<Ticket>
        implements TicketService {

    public TicketServiceImpl(String baseUrl) {
        super(TicketServiceApi.class, Ticket.class, baseUrl, "tickets");
    }

    @Override
    public ServiceResponse<Ticket> create(Ticket ticket) {
        var call = getServiceApi().addTicket(ticket.getFlightId(), ticket);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Double> getAverageSoldByCity(Long cityId) {
        var call = getServiceApi().getAverageSoldByCity(cityId);
        return getServerResponse(call);
    };

    private TicketServiceApi getServiceApi() {
        return (TicketServiceApi) getCrudServiceApi();
    }

}
