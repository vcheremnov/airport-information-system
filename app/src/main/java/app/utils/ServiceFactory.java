package app.utils;

import app.AppProperties;
import app.services.*;
import app.services.impl.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceFactory {

    public AirplaneService getAirplaneService() {
        return new AirplaneServiceImplImpl(AppProperties.getServerHostname());
    }

    public AirplaneTypeService getAirplaneTypeService() {
        return new AirplaneTypeServiceImpl(AppProperties.getServerHostname());
    }

    public ChiefService getChiefService() {
        return new ChiefServiceImpl(AppProperties.getServerHostname());
    }

    public CityService getCityService() {
        return new CityServiceImpl(AppProperties.getServerHostname());
    }

    public DepartmentService getDepartmentService() {
        return new DepartmentServiceImpl(AppProperties.getServerHostname());
    }

    public EmployeeService getEmployeeService() {
        return new EmployeeServiceImpl(AppProperties.getServerHostname());
    }

    public FlightDelayService getFlightDelayService() {
        return new FlightDelayServiceImpl(AppProperties.getServerHostname());
    }

    public FlightService getFlightService() {
        return new FlightServiceImpl(AppProperties.getServerHostname());
    }

    public MedicalExaminationService getMedicalExaminationService() {
        return new MedicalExaminationServiceImpl(AppProperties.getServerHostname());
    }

    public RepairService getRepairService() {
        return new RepairServiceImpl(AppProperties.getServerHostname());
    }

    public TeamService getTeamService() {
        return new TeamServiceImpl(AppProperties.getServerHostname());
    }

    public TechInspectionService getTechInspectionService() {
        return new TechInspectionServiceImpl(AppProperties.getServerHostname());
    }

    public TicketService getTicketService() {
        return new TicketServiceImpl(AppProperties.getServerHostname());
    }

}
