import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.ietf.jgss.GSSContext;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Main {
    @SneakyThrows
    public static void main(String[] args) {

        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://localhost:8080/")
                .build();

        AirportService service = retrofit.create(AirportService.class);
        Call<Employee> employeeCall = service.getEmployeeById(100L);
        Response<Employee> employeeResponse = employeeCall.execute();
        Employee employee = employeeResponse.body();

        Long id = employee.getId();

    }
}