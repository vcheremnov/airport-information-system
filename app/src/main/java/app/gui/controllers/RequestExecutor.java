package app.gui.controllers;

import app.services.ServiceResponse;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RequestExecutor {

    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

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
        private Optional<OnSuccessAction<T>> onSuccessAction = Optional.empty();
        private Optional<OnFailureAction> onFailureAction = Optional.empty();
        private Optional<Runnable> finalAction = Optional.empty();

        private RequestBuilder(ResponseBodySupplier<T> responseBodySupplier) {
            this.responseBodySupplier = responseBodySupplier;
        }

        public RequestBuilder<T> setOnSuccessAction(OnSuccessAction<T> onSuccessAction) {
            this.onSuccessAction = Optional.ofNullable(onSuccessAction);
            return this;
        }

        public RequestBuilder<T> setOnFailureAction(OnFailureAction onFailureAction) {
            this.onFailureAction = Optional.ofNullable(onFailureAction);
            return this;
        }

        public RequestBuilder<T> setFinalAction(Runnable finalAction) {
            this.finalAction = Optional.ofNullable(finalAction);
            return this;
        }

        public void submit() {
            executorService.submit(() -> {
                try {
                    ServiceResponse<T> serviceResponse = responseBodySupplier.getServiceResponse();
                    T responseBody = serviceResponse.getBody();
                    if (responseBody == null) {
                        onFailureAction.ifPresent(a -> a.run(serviceResponse.getErrorMessage()));
                    } else {
                        onSuccessAction.ifPresent(a -> a.run(responseBody));
                    }
                } catch (Exception e) {
                    onFailureAction.ifPresent(a -> a.run(e.getLocalizedMessage()));
                } finally {
                    finalAction.ifPresent(Runnable::run);
                }
            });
        }
    }
}
