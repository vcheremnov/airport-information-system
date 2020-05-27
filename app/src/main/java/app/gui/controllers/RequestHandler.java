package app.gui.controllers;

import app.services.ServiceResponse;
import lombok.experimental.UtilityClass;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@UtilityClass
public class RequestHandler {

    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @FunctionalInterface
    public interface ResponseBodySupplier<T> {
        ServiceResponse<T> getServiceResponse() throws Exception;
    }

    @FunctionalInterface
    public interface OnSuccessAction<T> {
        void run(T responseBody);
    }

    @FunctionalInterface
    public interface OnFailureAction {
        void run(String errorMessage);
    }

    public <T> void handleRequest(
        ResponseBodySupplier<T> responseBodySupplier,
        OnSuccessAction<T> onSuccessAction,
        OnFailureAction onFailureAction
    ) {
        executorService.submit(() -> {
            try {
                ServiceResponse<T> serviceResponse = responseBodySupplier.getServiceResponse();
                T responseBody = serviceResponse.getBody();
                if (responseBody == null) {
                    onFailureAction.run(serviceResponse.getErrorMessage());
                } else {
                    onSuccessAction.run(responseBody);
                }
            } catch (Exception e) {
                onFailureAction.run(e.getMessage());
            }
        });
    }
}
