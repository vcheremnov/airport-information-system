package app.services.impl;

import app.model.MedicalExamination;
import app.services.MedicalExaminationService;
import app.services.impl.api.MedicalExaminationServiceApi;

public class MedicalExaminationServiceImpl
        extends AbstractCrudServiceImpl<MedicalExamination>
        implements MedicalExaminationService {

    public MedicalExaminationServiceImpl(String baseUrl) {
        super(MedicalExaminationServiceApi.class, MedicalExamination.class,
                baseUrl, "medical-examinations");
    }


    private MedicalExaminationServiceApi getServiceApi() {
        return (MedicalExaminationServiceApi) getCrudServiceApi();
    }

}
