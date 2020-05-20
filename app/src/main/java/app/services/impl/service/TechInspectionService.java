package app.services.impl.service;

import app.model.TechInspection;
import app.services.AbstractCrudService;
import app.services.impl.api.TechInspectionServiceApi;

public class TechInspectionService extends AbstractCrudService<TechInspection> {

    public TechInspectionService() {
        super(TechInspectionServiceApi.class, TechInspection.class, "tech-inspections");
    }


    private TechInspectionServiceApi getServiceApi() {
        return (TechInspectionServiceApi) getCrudServiceApi();
    }

}
