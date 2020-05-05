package airport.controllers;

import airport.dtos.MedicalExaminationDto;
import airport.services.MedicalExaminationService;
import airport.services.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medical-examinations")
public class MedicalExaminationController extends AbstractController<MedicalExaminationDto, Long> {

    private final MedicalExaminationService medicalExaminationService;

    @Autowired
    public MedicalExaminationController(MedicalExaminationService MedicalExaminationService) {
        this.medicalExaminationService = MedicalExaminationService;
    }

    @Override
    protected Service<MedicalExaminationDto, Long> getService() {
        return medicalExaminationService;
    }

}
