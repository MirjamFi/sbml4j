package io.swagger.api;

import io.swagger.api.*;
import rest.model.*;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import rest.model.Kgml;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.validation.constraints.*;
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public abstract class KgmlApiService {
    public abstract Response getKgml(String fileId,SecurityContext securityContext) throws NotFoundException;
    public abstract Response registerKgml(Kgml body,SecurityContext securityContext) throws NotFoundException;
    public abstract Response updateKgml(Kgml body,String fileId,SecurityContext securityContext) throws NotFoundException;
}
