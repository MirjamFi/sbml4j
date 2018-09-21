package io.swagger.api;

import rest.model.*;
import io.swagger.api.KgmlApiService;
import io.swagger.api.factories.KgmlApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import rest.model.Kgml;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/Kgml")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public class KgmlApi  {
   private final KgmlApiService delegate;

   public KgmlApi(@Context ServletConfig servletContext) {
      KgmlApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("KgmlApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (KgmlApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = KgmlApiServiceFactory.getKgmlApi();
      }

      this.delegate = delegate;
   }

    @GET
    
    
    
    @Operation(summary = "Retrieve an existing kgml file", description = "", tags={ "kgml" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        
        @ApiResponse(responseCode = "404", description = "kgml not found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response getKgml(@Parameter(description = "ID of the file to get",required=true) @PathParam("fileId") String fileId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getKgml(fileId,securityContext);
    }
    @POST
    
    
    
    @Operation(summary = "Register kgml file in the system", description = "", tags={ "kgml" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "201", description = "Item created"),
        
        @ApiResponse(responseCode = "405", description = "Invalid input") })
    public Response registerKgml(@Parameter(description = "kgml object that needs to be added to the store" ,required=true) Kgml body

,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.registerKgml(body,securityContext);
    }
    @PUT
    
    
    
    @Operation(summary = "Update an existing kgml file", description = "", tags={ "kgml" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "File updated"),
        
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        
        @ApiResponse(responseCode = "404", description = "kgml not found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response updateKgml(@Parameter(description = "kgml object that needs to be added to the store" ,required=true) Kgml body

,@Parameter(description = "ID of the file to get",required=true) @PathParam("fileId") String fileId
,@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.updateKgml(body,fileId,securityContext);
    }
}
