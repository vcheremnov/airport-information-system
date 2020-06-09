package app.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class ServiceResponse<T> {

    private T body;
    private boolean isSuccessful;
    private String errorMessage;

    public boolean isSuccessful() {
        return isSuccessful;
    }

    public interface ResponseBodyMapper<X, Y> {
        Y map(X responseBody);
    }

    public <Y> ServiceResponse<Y> map(ResponseBodyMapper<T, Y> responseBodyMapper) {
        return new ServiceResponse<>(
                responseBodyMapper.map(body),
                isSuccessful,
                errorMessage
        );
    }

}
