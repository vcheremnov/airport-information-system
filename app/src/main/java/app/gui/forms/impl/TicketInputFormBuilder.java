package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.controllers.interfaces.SuccessAction;
import app.gui.custom.ChoiceItem;
import app.model.Employee;
import app.model.Flight;
import app.model.Team;
import app.model.Ticket;
import app.model.types.TicketStatus;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.stream.Collectors;

public class TicketInputFormBuilder extends AbstractEntityInputFormBuilder<Ticket> {

    public TicketInputFormBuilder(RequestExecutor requestExecutor) {
        super(Ticket::new, ServiceFactory.getTicketService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Ticket ticket,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<Ticket> controller
    ) {

        if (formType == FormType.CREATION_FORM) {
            if (!isContextWindow) {
                Long flightId = ticket.getFlightId();
                controller.addIntegerField(
                        "№ рейса",
                        flightId == null ? 0 : flightId.intValue(),
                        value -> ticket.setFlightId(value.longValue())
                );
            }

            Long passengerId = ticket.getPassenger().getId();
            controller.addIntegerField(
                    "№ пассажира",
                    passengerId == null ? 0 : passengerId.intValue(),
                    value -> ticket.getPassenger().setId(value.longValue())
            );
        }

        ChoiceItemSupplier<TicketStatus> ticketStatusSupplier = () -> Arrays.stream(TicketStatus.values())
                .map(s -> new ChoiceItem<>(s, TicketStatus.toLocalizedString(s)))
                .collect(Collectors.toList());

        controller.addChoiceBox(
                "Статус билета",
                ticket.getStatus(),
                ticket::setStatus,
                ticketStatusSupplier
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить новый билет";
    }

    @Override
    protected String getEditFormWindowTitle(Ticket ticket) {
        return String.format(
                "Билет №%d на рейс №%d - изменить",
                ticket.getId(),
                ticket.getFlightId()
        );
    }

}
