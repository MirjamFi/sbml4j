package io.swagger.api.factories;

import io.swagger.api.PipelineApiService;
import io.swagger.api.impl.PipelineApiServiceImpl;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public class PipelineApiServiceFactory {
    private final static PipelineApiService service = new PipelineApiServiceImpl();

    public static PipelineApiService getPipelineApi() {
        return service;
    }
}
