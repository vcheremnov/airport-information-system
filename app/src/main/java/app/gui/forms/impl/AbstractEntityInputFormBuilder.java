package app.gui.forms.impl;

import app.gui.controllers.EntityInputFormController;
import app.gui.controllers.FxmlLoaderFactory;
import app.gui.controllers.interfaces.ChoiceItemSupplier;
import app.gui.custom.ChoiceItem;
import app.gui.forms.EntityInputFormBuilder;
import app.model.Entity;
import app.services.Service;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;
import app.utils.RequestExecutor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public abstract class AbstractEntityInputFormBuilder<E extends Entity>
                implements EntityInputFormBuilder<E> {

    private final RequestExecutor requestExecutor;
    private final Supplier<E> newEntitySupplier;
    private final Service<E> entityService;

    @AllArgsConstructor
    private static class EntityInputForm<T extends Entity> {

        private Parent rootNode;
        private EntityInputFormController<T> controller;

    }

    protected enum FormType {
        EDIT_FORM,
        CREATION_FORM
    }

    protected AbstractEntityInputFormBuilder(
            Supplier<E> newEntitySupplier,
            Service<E> entityService,
            RequestExecutor requestExecutor
    ) {
        this.requestExecutor = requestExecutor;
        this.newEntitySupplier = newEntitySupplier;
        this.entityService = entityService;
    }

    @Override
    public Stage buildCreationFormWindow() {
        E entity = newEntitySupplier.get();
        var entityInputForm = createFormController(entity, FormType.CREATION_FORM);
        fillInputForm(entity, FormType.CREATION_FORM, entityInputForm.controller);
        return createStage(entityInputForm.rootNode, getCreationFormWindowTitle());
    }

    @Override
    public Stage buildEditFormWindow(E entity) {
        var entityInputForm = createFormController(entity, FormType.EDIT_FORM);
        fillInputForm(entity, FormType.EDIT_FORM, entityInputForm.controller);
        return createStage(entityInputForm.rootNode, getEditFormWindowTitle(entity));
    }

    protected <X extends Entity, Y> ChoiceItemSupplier<Y> makeChoiceItemSupplierFromEntities(
            Service<X> entityService,
            Function<X, ChoiceItem<Y>> entityToChoiceItemMapper,
            String errorMessage
    ) {
        return makeChoiceItemSupplierFromEntities(
                entityService,
                x -> true,
                entityToChoiceItemMapper,
                errorMessage
        );
    }

    protected <X extends Entity, Y> ChoiceItemSupplier<Y> makeChoiceItemSupplierFromEntities(
        Service<X> entityService,
        Predicate<X> entityFilterPredicate,
        Function<X, ChoiceItem<Y>> entityToChoiceItemMapper,
        String errorMessage
    ) {
        return () -> {

            Page<X> page = entityService.getAll(PageInfo.getUnlimitedPageInfo()).getBody();
            Objects.requireNonNull(page, errorMessage);

            return page.getElementList().stream()
                    .filter(entityFilterPredicate)
                    .map(entityToChoiceItemMapper)
                    .collect(Collectors.toList());

        };
    }

    protected abstract void fillInputForm(
            E entity,
            FormType formType,
            EntityInputFormController<E> controller
    );

    protected abstract String getCreationFormWindowTitle();

    protected abstract String getEditFormWindowTitle(E entity);

    @SneakyThrows
    private EntityInputForm<E> createFormController(E entity, FormType formType) {
        var fxmlLoader = FxmlLoaderFactory.createEntityInputFormLoader();
        Parent rootNode = fxmlLoader.load();
        EntityInputFormController<E> controller = fxmlLoader.getController();

        switch (formType) {
            case CREATION_FORM:
                controller.init(entity, entityService::create, requestExecutor);
                break;
            case EDIT_FORM:
                controller.init(entity, entityService::save, requestExecutor);
                break;
        }

        return new EntityInputForm<>(rootNode, controller);
    }

    private static Stage createStage(Parent rootNode, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Scene scene = new Scene(rootNode);
        stage.setScene(scene);
        stage.sizeToScene();
        return stage;
    }

}
