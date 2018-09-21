package io.swagger.api;

import rest.model.*;
import io.swagger.api.PipelineApiService;
import io.swagger.api.factories.PipelineApiServiceFactory;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import rest.model.NodeEdgeListEntry;

import java.util.Map;
import java.util.List;
import io.swagger.api.NotFoundException;

import java.io.InputStream;

//import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
//import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.ServletConfig;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.*;
import javax.validation.constraints.*;

@Path("/Pipeline")


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaJerseyServerCodegen", date = "2018-09-21T12:10:20.804Z[GMT]")public class PipelineApi  {
   private final PipelineApiService delegate;

   public PipelineApi(@Context ServletConfig servletContext) {
      PipelineApiService delegate = null;

      if (servletContext != null) {
         String implClass = servletContext.getInitParameter("PipelineApi.implementation");
         if (implClass != null && !"".equals(implClass.trim())) {
            try {
               delegate = (PipelineApiService) Class.forName(implClass).newInstance();
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         } 
      }

      if (delegate == null) {
         delegate = PipelineApiServiceFactory.getPipelineApi();
      }

      this.delegate = delegate;
   }

    @GET
    
    
    @Produces({ "application/json" })
    @Operation(summary = "", description = "retrieve the full network currently stored as node and edgelist", tags={ "pipeline" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "retrieved network", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NodeEdgeListEntry.class)))),
        
        @ApiResponse(responseCode = "404", description = "no entries found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response getFullNetwork(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getFullNetwork(securityContext);
    }
    @GET
    
    
    @Produces({ "application/json" })
    @Operation(summary = "", description = "retrieve the full network currently stored as node and edgelist", tags={ "pipeline" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "retrieved network", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NodeEdgeListEntry.class)))),
        
        @ApiResponse(responseCode = "404", description = "no entries found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response getMetabolicNetwork(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getMetabolicNetwork(securityContext);
    }
    @GET
    
    
    @Produces({ "application/json" })
    @Operation(summary = "", description = "retrieve the full network currently stored as node and edgelist", tags={ "pipeline" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "retrieved network", content = @Content(array = @ArraySchema(schema = @Schema(implementation = NodeEdgeListEntry.class)))),
        
        @ApiResponse(responseCode = "404", description = "no entries found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response getNonMetabolicNetwork(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.getNonMetabolicNetwork(securityContext);
    }
    @GET
    
    
    
    @Operation(summary = "", description = "", tags={ "pipeline" })
    @ApiResponses(value = { 
        @ApiResponse(responseCode = "200", description = "File updated"),
        
        @ApiResponse(responseCode = "400", description = "Invalid ID supplied"),
        
        @ApiResponse(responseCode = "404", description = "kgml not found"),
        
        @ApiResponse(responseCode = "405", description = "Validation exception") })
    public Response persistAll(@Context SecurityContext securityContext)
    throws NotFoundException {
        return delegate.persistAll(securityContext);
    }
}
