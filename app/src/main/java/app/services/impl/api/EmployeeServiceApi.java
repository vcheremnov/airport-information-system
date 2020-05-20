package app.services.impl.api;

import app.model.Employee;
import app.model.MedicalExamination;
import app.services.CrudServiceApi;
import app.services.pagination.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

import java.util.Map;

public interface EmployeeServiceApi extends CrudServiceApi {

    @GET("employees/by-med-exam-result")
    Call<Page<Employee>> getByMedExamResult(
            @Query("year") Integer year,
            @Query("isPassed") Boolean isPassed,
            @QueryMap Map<String, Object> pageInfo
    );

    @GET("employees/{id}/med-exams")
    Call<Page<MedicalExamination>> getMedicalExaminations(
            @Path("id") Long employeeId
    );

}
