package app.services.impl;

import app.AppProperties;
import app.model.Entity;
import app.services.Service;
import app.services.ServiceResponse;
import app.services.filters.Filter;
import app.services.impl.api.CrudServiceApi;
import app.services.pagination.Page;
import app.services.pagination.PageInfo;
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

public abstract class AbstractCrudServiceImpl<T extends Entity> implements Service<T> {

    private static final Gson gson = new Gson();

    private final String urlRoot;
    private final CrudServiceApi crudServiceApi;
    private final Class<T> entityClass;

    protected AbstractCrudServiceImpl(
            Class<? extends CrudServiceApi> serviceApiClass,
            Class<T> entityClass,
            String baseUrl,
            String urlRoot
    ) {
        this.urlRoot = urlRoot;
        this.entityClass = entityClass;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        crudServiceApi = retrofit.create(serviceApiClass);
    }

    @Override
    public ServiceResponse<Long> countAll() {
        var call = crudServiceApi.countAll(urlRoot);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<T> getById(Long id) {
        var call = crudServiceApi.getById(urlRoot, id);
        return getServerResponse(call, entityClass);
    }

    @Override
    public ServiceResponse<Page<T>> getAll(PageInfo pageInfo) {
        var call = crudServiceApi.getAll(urlRoot, PageInfo.toMap(pageInfo));
        return getServerResponse(call, TypeFactory.getPageType(entityClass));
    }

    @Override
    public ServiceResponse<List<T>> getAllById(Collection<Long> idCollection) {
        var call = crudServiceApi.getAllById(urlRoot, idCollection);
        return getServerResponse(call, TypeFactory.getListType(entityClass));
    }

    @Override
    public ServiceResponse<Page<T>> search(Filter<T> filter, PageInfo pageInfo) {
        var call = crudServiceApi.search(urlRoot, PageInfo.toMap(pageInfo), gson.toJsonTree(filter));
        return getServerResponse(call, TypeFactory.getPageType(entityClass));
    }

    @Override
    public ServiceResponse<T> create(T entity) {
        var call = crudServiceApi.create(urlRoot, gson.toJsonTree(entity));
        return getServerResponse(call, entityClass);
    }

    @Override
    public ServiceResponse<T> save(Long id, T entity) {
        var call = crudServiceApi.save(urlRoot, id, gson.toJsonTree(entity));
        return getServerResponse(call, entityClass);
    }

    @Override
    public ServiceResponse<List<T>> saveAll(Collection<T> entityCollection) {
        var call = crudServiceApi.saveAll(urlRoot, gson.toJsonTree(entityCollection));
        return getServerResponse(call, TypeFactory.getListType(entityClass));
    }

    @Override
    public ServiceResponse<Void> deleteById(Long id) {
        var call = crudServiceApi.deleteById(urlRoot, id);
        return getServerResponse(call);
    }

    @Override
    public ServiceResponse<Void> deleteAllById(Collection<Long> idCollection) {
        var call = crudServiceApi.deleteAllById(urlRoot, idCollection);
        return getServerResponse(call);
    }

    @SneakyThrows
    protected <X> ServiceResponse<X> getServerResponse(Call<X> call) {
        Response<X> response = call.execute();

        if (response.isSuccessful()) {
            return new ServiceResponse<>(response.body(), response.code(), null);
        }

        return new ServiceResponse<>(null, response.code(), getErrorMessage(response));
    }

    @SneakyThrows
    protected <X> ServiceResponse<X> getServerResponse(Call<JsonElement> call, Type bodyType) {
        Response<JsonElement> response = call.execute();
        JsonElement jsonResponseBody = response.body();

        if (response.isSuccessful()) {
            X responseBody = gson.fromJson(jsonResponseBody, bodyType);
            return new ServiceResponse<>(responseBody, response.code(), null);
        }

        return new ServiceResponse<>(null, response.code(), getErrorMessage(response));
    }

    @SneakyThrows
    private String getErrorMessage(Response<?> response) {
        if (response.errorBody() == null) {
            return "";
        }

        String jsonErrorBody = response.errorBody().string();
        ErrorResponseBody errorBody = gson.fromJson(jsonErrorBody, ErrorResponseBody.class);
        if (errorBody == null) {
            return "";
        }

        return errorBody.message;
    }

    private static class ErrorResponseBody {
        String message;
    }

    protected CrudServiceApi getCrudServiceApi() {
        return crudServiceApi;
    }

}
