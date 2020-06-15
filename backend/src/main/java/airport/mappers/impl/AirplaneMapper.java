package airport.mappers.impl;

import airport.dtos.AirplaneDto;
import airport.dtos.AirplaneTypeDto;
import airport.dtos.TeamDto;
import airport.entities.*;
import airport.mappers.Mapper;
import airport.repositories.AirplaneTypeRepository;
import airport.repositories.TeamRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class AirplaneMapper extends AbstractMapper<Airplane, AirplaneDto, Long> {

    private final Mapper<Team, TeamDto, Long> teamMapper;
    private final TeamRepository teamRepository;
    private final AirplaneTypeRepository airplaneTypeRepository;
    private final Mapper<AirplaneType, AirplaneTypeDto, Long> airplaneTypeMapper;

    @Autowired
    public AirplaneMapper(ModelMapper mapper,
                          Mapper<Team, TeamDto, Long> teamMapper,
                          TeamRepository teamRepository,
                          AirplaneTypeRepository airplaneTypeRepository,
                          Mapper<AirplaneType, AirplaneTypeDto, Long> airplaneTypeMapper) {
        super(mapper, Airplane.class, AirplaneDto.class);
        this.teamMapper = teamMapper;
        this.teamRepository = teamRepository;
        this.airplaneTypeRepository = airplaneTypeRepository;
        this.airplaneTypeMapper = airplaneTypeMapper;
    }

    @PostConstruct
    public void setupMapper() {
        skipDtoField(AirplaneDto::setAirplaneType);
        skipDtoField(AirplaneDto::setPilotTeam);
        skipDtoField(AirplaneDto::setTechTeam);
        skipDtoField(AirplaneDto::setServiceTeam);

//        skipEntityField(Airplane::setFlightsNumber);
        skipEntityField(Airplane::setAirplaneType);
        skipEntityField(Airplane::setPilotTeam);
        skipEntityField(Airplane::setTechTeam);
        skipEntityField(Airplane::setServiceTeam);
        skipEntityField(Airplane::setRepairs);
        skipEntityField(Airplane::setTechInspections);
        skipEntityField(Airplane::setFlights);
    }

    @Override
    protected void mapSpecificFields(Airplane sourceEntity, AirplaneDto destinationDto) {
        destinationDto.setAirplaneType(airplaneTypeMapper.toDto(sourceEntity.getAirplaneType()));
        destinationDto.setPilotTeam(teamMapper.toDto(sourceEntity.getPilotTeam()));
        destinationDto.setTechTeam(teamMapper.toDto(sourceEntity.getTechTeam()));
        destinationDto.setServiceTeam(teamMapper.toDto(sourceEntity.getServiceTeam()));
    }

    @Override
    protected void mapSpecificFields(AirplaneDto sourceDto, Airplane destinationEntity) {
        destinationEntity.setAirplaneType(
                getEntityByIdOrThrow(airplaneTypeRepository, sourceDto.getAirplaneType().getId())
        );
        destinationEntity.setPilotTeam(
                getEntityByIdOrThrow(teamRepository, sourceDto.getPilotTeam().getId())
        );
        destinationEntity.setTechTeam(
                getEntityByIdOrThrow(teamRepository, sourceDto.getTechTeam().getId())
        );
        destinationEntity.setServiceTeam(
                getEntityByIdOrThrow(teamRepository, sourceDto.getServiceTeam().getId())
        );
    }
}
