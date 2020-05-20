package app.services;

import app.model.Entity;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;
import app.services.pagination.PageSort;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import lombok.SneakyThrows;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class AbstractCrudService<T extends Entity> {

    // TODO: вынести url в application.properties
    private static final String BASE_URL = "http://localhost:8080/";
    private static final Retrofit retrofit;
    private static final Gson gson;

    private final String urlRoot;
    private final CrudServiceApi crudServiceApi;
    private final Class<T> entityClass;

    static {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        gson = new Gson();
    }

    protected AbstractCrudService(
            Class<? extends CrudServiceApi> serviceApiClass,
            Class<T> entityClass,
            String urlRoot
    ) {
        this.urlRoot = urlRoot;
        this.entityClass = entityClass;
        crudServiceApi = retrofit.create(serviceApiClass);
    }

    public ServiceResponse<Long> countAll() {
        var call = crudServiceApi.countAll(urlRoot);
        return getServerResponse(call);
    }

    public ServiceResponse<T> getById(Long id) {
        var call = crudServiceApi.getById(urlRoot, id);
        return getServerResponse(call, entityClass);
    }

    public ServiceResponse<Page<T>> getAll(PageInfo pageInfo) {
        var call = crudServiceApi.getAll(urlRoot, PageInfo.toMap(pageInfo));
        return getServerResponse(call, TypeFactory.getPageType(entityClass));
    }

    public ServiceResponse<List<T>> getAllById(Collection<Long> idCollection) {
        var call = crudServiceApi.getAllById(urlRoot, idCollection);
        return getServerResponse(call, TypeFactory.getListType(entityClass));
    }

    public ServiceResponse<Page<T>> search(PageInfo pageInfo, Filter<T> filter) {
        var call = crudServiceApi.search(urlRoot, PageInfo.toMap(pageInfo), gson.toJsonTree(filter));
        return getServerResponse(call, TypeFactory.getPageType(entityClass));
    }

    public ServiceResponse<T> create(T entity) {
        var call = crudServiceApi.create(urlRoot, gson.toJsonTree(entity));
        return getServerResponse(call, entityClass);
    }

    public ServiceResponse<T> save(Long id, T entity) {
        var call = crudServiceApi.save(urlRoot, id, gson.toJsonTree(entity));
        return getServerResponse(call, entityClass);
    }

    public ServiceResponse<List<T>> saveAll(Collection<T> entityCollection) {
        var call = crudServiceApi.saveAll(urlRoot, gson.toJsonTree(entityCollection));
        return getServerResponse(call, TypeFactory.getListType(entityClass));
    }

    public ServiceResponse<Void> deleteById(Long id) {
        var call = crudServiceApi.deleteById(urlRoot, id);
        return getServerResponse(call);
    }

    public ServiceResponse<Void> deleteAllById(Collection<Long> idCollection) {
        var call = crudServiceApi.deleteAllById(urlRoot, idCollection);
        return getServerResponse(call);
    }

    @SneakyThrows
    protected <X> ServiceResponse<X> getServerResponse(Call<X> call) {
        Response<X> response = call.execute();
        X responseBody = response.body();
        int statusCode = response.code();

        return new ServiceResponse<>(statusCode, responseBody);
    }

    @SneakyThrows
    protected <X> ServiceResponse<X> getServerResponse(Call<JsonElement> call, Type bodyType) {
        Response<JsonElement> response = call.execute();
        JsonElement jsonResponseBody = response.body();

        X responseBody = gson.fromJson(jsonResponseBody, bodyType);
        int statusCode = response.code();

        return new ServiceResponse<>(statusCode, responseBody);
    }

    protected CrudServiceApi getCrudServiceApi() {
        return crudServiceApi;
    }

}
