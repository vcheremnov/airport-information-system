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
    private int httpStatusCode;
    private String errorMessage;

}
