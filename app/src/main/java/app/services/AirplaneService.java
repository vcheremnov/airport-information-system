package app.services;

import app.model.Airplane;
import app.model.Repair;
import app.model.TechInspection;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;

public interface AirplaneService extends Service<Airplane> {

    ServiceResponse<Page<Repair>> getRepairs(Long airplaneId, PageInfo pageInfo);

    ServiceResponse<Page<TechInspection>> getTechInspections(Long airplaneId, PageInfo pageInfo);

}

