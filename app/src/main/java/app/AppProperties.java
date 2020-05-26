package app;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AppProperties {

    // TODO: вынести в application.properties
    public String getServerHostname() {
        return "http://localhost:8080/";
    }

}
