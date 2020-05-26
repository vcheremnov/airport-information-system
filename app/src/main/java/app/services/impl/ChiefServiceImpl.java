package app.services.impl;

import app.model.Chief;
import app.services.ChiefService;
import app.services.impl.api.ChiefServiceApi;

public class ChiefServiceImpl
        extends AbstractCrudServiceImpl<Chief>
        implements ChiefService {

    public ChiefServiceImpl(String baseUrl) {
        super(ChiefServiceApi.class, Chief.class, baseUrl, "chiefs");
    }


    private ChiefServiceApi getServiceApi() {
        return (ChiefServiceApi) getCrudServiceApi();
    }

}
