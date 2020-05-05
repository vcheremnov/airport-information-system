package airport.services.impl;

import airport.dtos.MedicalExaminationDto;
import airport.entities.MedicalExamination;
import airport.mappers.Mapper;
import airport.repositories.MedicalExaminationRepository;
import airport.services.MedicalExaminationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class MedicalExaminationServiceImpl
        extends AbstractService<MedicalExamination, MedicalExaminationDto, Long>
        implements MedicalExaminationService {

    private final MedicalExaminationRepository repository;
    private final Mapper<MedicalExamination, MedicalExaminationDto, Long> mapper;

    @Autowired
    public MedicalExaminationServiceImpl(MedicalExaminationRepository repository,
                           Mapper<MedicalExamination, MedicalExaminationDto, Long> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    protected JpaRepository<MedicalExamination, Long> getRepository() {
        return repository;
    }

    @Override
    protected Mapper<MedicalExamination, MedicalExaminationDto, Long> getMapper() {
        return mapper;
    }

}
