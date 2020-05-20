package app.services.impl.service;

import app.model.Chief;
import app.services.AbstractCrudService;
import app.services.impl.api.ChiefServiceApi;

public class ChiefService extends AbstractCrudService<Chief> {

    public ChiefService() {
        super(ChiefServiceApi.class, Chief.class, "chiefs");
    }


    private ChiefServiceApi getServiceApi() {
        return (ChiefServiceApi) getCrudServiceApi();
    }

}
