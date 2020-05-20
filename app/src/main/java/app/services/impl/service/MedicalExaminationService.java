package app.services.impl.service;

import app.model.MedicalExamination;
import app.services.AbstractCrudService;
import app.services.impl.api.MedicalExaminationServiceApi;

public class MedicalExaminationService extends AbstractCrudService<MedicalExamination> {

    public MedicalExaminationService() {
        super(MedicalExaminationServiceApi.class, MedicalExamination.class, "medical-examinations");
    }


    private MedicalExaminationServiceApi getServiceApi() {
        return (MedicalExaminationServiceApi) getCrudServiceApi();
    }

}
