package app.services;

import app.model.Ticket;

public interface TicketService extends Service<Ticket> {

    ServiceResponse<Double> getAverageSoldByCity(Long cityId);

}
