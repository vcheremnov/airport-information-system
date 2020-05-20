package app.services.impl.service;

import app.model.Repair;
import app.services.AbstractCrudService;
import app.services.impl.api.RepairServiceApi;

public class RepairService extends AbstractCrudService<Repair> {

    public RepairService() {
        super(RepairServiceApi.class, Repair.class, "repairs");
    }


    private RepairServiceApi getServiceApi() {
        return (RepairServiceApi) getCrudServiceApi();
    }

}
