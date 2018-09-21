package io.swagger.api.factories;

import io.swagger.api.KgmlApiService;
import io.swagger.api.impl.KgmlApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public class KgmlApiServiceFactory {
    private final static KgmlApiService service = new KgmlApiServiceImpl();

    public static KgmlApiService getKgmlApi() {
        return service;
    }
}
