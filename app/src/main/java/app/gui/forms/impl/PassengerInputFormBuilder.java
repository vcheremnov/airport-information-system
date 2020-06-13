package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.model.Passenger;
import app.model.types.Sex;
import app.utils.RequestExecutor;
import app.utils.ServiceFactory;

import java.util.Arrays;
import java.util.stream.Collectors;

public class PassengerInputFormBuilder extends AbstractEntityInputFormBuilder<Passenger> {

    public PassengerInputFormBuilder(RequestExecutor requestExecutor) {
        super(Passenger::new, ServiceFactory.getPassengerService(), requestExecutor);
    }

    @Override
    protected void fillInputForm(
            Passenger passenger,
            FormType formType,
            boolean isContextWindow,
            EntityInputFormController<Passenger> controller
    ) {

        ChoiceItemSupplier<Sex> sexChoiceItemSupplier = () -> Arrays.stream(Sex.values())
                .map(s -> new ChoiceItem<>(s, Sex.toLocalizedString(s)))
                .collect(Collectors.toList());

        controller.addTextField(
                "ФИО пассажира",
                passenger.getName(),
                passenger::setName
        );

        controller.addChoiceBox(
                "Пол",
                passenger.getSex(),
                passenger::setSex,
                sexChoiceItemSupplier
        );

        controller.addDateField(
                "Дата рождения",
                passenger.getBirthDate(),
                passenger::setBirthDate
        );

    }

    @Override
    protected String getCreationFormWindowTitle() {
        return "Добавить нового пассажира";
    }

    @Override
    protected String getEditFormWindowTitle(Passenger passenger) {
        return String.format("Пассажир %s - изменить", passenger.getName());
    }

}
