package app.gui.controllers;

import app.services.ServiceResponse;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestExecutor {

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

    public <T> RequestBuilder<T> makeRequest(ResponseBodySupplier<T> responseBodySupplier) {
        return this.new RequestBuilder<>(responseBodySupplier);
    }

    public void shutdown() {
        executorService.shutdownNow();
    }

    public class RequestBuilder<T> {

        private final ResponseBodySupplier<T> responseBodySupplier;
        private OnSuccessAction<T> onSuccessAction;
        private OnFailureAction onFailureAction;
        private Runnable finalAction;

        private RequestBuilder(ResponseBodySupplier<T> responseBodySupplier) {
            this.responseBodySupplier = responseBodySupplier;
        }

        public RequestBuilder<T> setOnSuccessAction(OnSuccessAction<T> onSuccessAction) {
            this.onSuccessAction = onSuccessAction;
            return this;
        }

        public RequestBuilder<T> setOnFailureAction(OnFailureAction onFailureAction) {
            this.onFailureAction = onFailureAction;
            return this;
        }

        public RequestBuilder<T> setFinalAction(Runnable finalAction) {
            this.finalAction = finalAction;
            return this;
        }

        public void submit() {
            executorService.submit(() -> {
                try {
                    ServiceResponse<T> serviceResponse = responseBodySupplier.getServiceResponse();
                    T responseBody = serviceResponse.getBody();
                    if (responseBody == null) {
                        if (onFailureAction != null) {
                            onFailureAction.run(serviceResponse.getErrorMessage());
                        }
                    } else if (onSuccessAction != null) {
                        onSuccessAction.run(responseBody);
                    }
                } catch (Exception e) {
                    if (onFailureAction != null) {
                        onFailureAction.run(e.getMessage());
                    }
                } finally {
                    if (finalAction != null) {
                        finalAction.run();
                    }
                }
            });
        }
    }
}
