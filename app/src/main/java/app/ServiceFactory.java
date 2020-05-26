package app;

import app.services.*;
import app.services.impl.*;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ServiceFactory {

    public AirplaneService getAirplaneService() {
        return new AirplaneServiceImplImpl();
    }

    public AirplaneTypeService getAirplaneTypeService() {
        return new AirplaneTypeServiceImpl();
    }

    public ChiefService getChiefService() {
        return new ChiefServiceImpl();
    }

    public CityService getCityService() {
        return new CityServiceImpl();
    }

    public DepartmentService getDepartmentService() {
        return new DepartmentServiceImpl();
    }

    public EmployeeService getEmployeeService() {
        return new EmployeeServiceImpl();
    }

    public FlightDelayService getFlightDelayService() {
        return new FlightDelayServiceImpl();
    }

    public FlightService getFlightService() {
        return new FlightServiceImpl();
    }

    public MedicalExaminationService getMedicalExaminationService() {
        return new MedicalExaminationServiceImpl();
    }

    public PassengerService getPassengerService() {
        return new PassengerServiceImpl();
    }

    public RepairService getRepairService() {
        return new RepairServiceImpl();
    }

    public TeamService getTeamService() {
        return new TeamServiceImpl();
    }

    public TechInspectionService getTechInspectionService() {
        return new TechInspectionServiceImpl();
    }

    public TicketService getTicketService() {
        return new TicketServiceImpl();
    }

}
