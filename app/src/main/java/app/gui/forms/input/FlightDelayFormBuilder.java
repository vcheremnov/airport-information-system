package app.gui.forms.input;

import app.gui.AlertDialogFactory;
import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.FxmlLoaderFactory;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.controllers.interfaces.SuccessAction;
import app.gui.custom.ChoiceItem;
import app.gui.forms.StageFactory;
import app.model.Flight;
import app.model.parameters.FlightDelayInfo;
import app.model.types.FlightDelayReason;
import app.services.FlightService;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;
import javafx.scene.Parent;
import javafx.stage.Stage;
import lombok.SneakyThrows;

import java.util.Arrays;
import java.util.stream.Collectors;

public class FlightDelayFormBuilder {

    private final FlightService flightService = ServiceFactory.getFlightService();
    private final RequestExecutor requestExecutor;

    public FlightDelayFormBuilder(RequestExecutor requestExecutor) {
        this.requestExecutor = requestExecutor;
    }

    @SneakyThrows
    public Stage createFlightDelayFormWindow(Flight flight, SuccessAction onSuccessAction) {
        var fxmlLoader = FxmlLoaderFactory.createEntityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        EntityInputFormController<FlightDelayInfo> controller = fxmlLoader.getController();
        FlightDelayInfo newFlightDelayInfo = new FlightDelayInfo();
        controller.init(
                newFlightDelayInfo,
                flightDelayInfo -> flightService
                        .delayFlight(flight.getId(), flightDelayInfo)
                        .map(responseBody -> null),
                onSuccessAction,
                errorMessage -> AlertDialogFactory.showErrorAlertDialog(
                        String.format("Не удалось задержать рейс №%d", flight.getId()),
                        errorMessage
                ),
                requestExecutor
        );

        fillFlightDelayForm(flight, newFlightDelayInfo, controller);
        return StageFactory.createStage(rootNode, String.format("Задержка рейса №%d", flight.getId()));
    }

    private void fillFlightDelayForm(
            Flight flight,
            FlightDelayInfo flightDelayInfo,
            EntityInputFormController<FlightDelayInfo> controller
    ) {

        controller.addChoiceBox(
                "Причина задержки",
                null,
                flightDelayInfo::setDelayReason,
                FlightDelayReason::getChoiceItems
        );

        controller.addDateTimeField(
                "Новое время рейса",
                flight.getFlightTime(),
                flightDelayInfo::setNewFlightTime
        );

    }

}
