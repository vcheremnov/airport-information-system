package app.services.impl;

import app.model.Airplane;
import app.model.Repair;
import app.model.TechInspection;
import app.services.AirplaneService;
import app.services.ServiceResponse;
import app.services.impl.api.AirplaneServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public class AirplaneServiceImplImpl
        extends AbstractCrudServiceImpl<Airplane>
        implements AirplaneService {

    public AirplaneServiceImplImpl() {
        super(AirplaneServiceApi.class, Airplane.class, "airplanes");
    }

    @Override
    public ServiceResponse<Page<Repair>> getRepairs(Long airplaneId, PageInfo pageInfo) {
        var call = getServiceApi().getRepairs(airplaneId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Page<TechInspection>> getTechInspections(Long airplaneId, PageInfo pageInfo) {
        var call = getServiceApi().getTechInspections(airplaneId, PageInfo.toMap(pageInfo));
        return getServerResponse(call);
    }


    private AirplaneServiceApi getServiceApi() {
        return (AirplaneServiceApi) getCrudServiceApi();
    }

}
