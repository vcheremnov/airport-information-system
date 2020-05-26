package app.services.impl;

import app.model.TechInspection;
import app.services.TechInspectionService;
import app.services.impl.api.TechInspectionServiceApi;

public class TechInspectionServiceImpl
        extends AbstractCrudServiceImpl<TechInspection>
        implements TechInspectionService {

    public TechInspectionServiceImpl(String baseUrl) {
        super(TechInspectionServiceApi.class, TechInspection.class, baseUrl, "tech-inspections");
    }


    private TechInspectionServiceApi getServiceApi() {
        return (TechInspectionServiceApi) getCrudServiceApi();
    }

}
