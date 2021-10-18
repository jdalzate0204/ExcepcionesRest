package co.edu.unicundi.excepcionesrest.exception;

import java.util.*;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

/**
 *
 * @author James Daniel Alzate Rios
 * @author Paula Alejandra Guzman Cruz
 */
@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();
        ExceptionWrapper ew;
        
        if (exception instanceof EmptyStackException) {
            return Response.status(Response.Status.NO_CONTENT).build();
        } 
        else if (exception instanceof IllegalArgumentException) {
            ew = new ExceptionWrapper(Response.Status.BAD_REQUEST.getStatusCode(), 
                                      Response.Status.BAD_REQUEST.getReasonPhrase(), 
                                      exception.getMessage(), 
                                      "");
            return Response.status(Response.Status.BAD_REQUEST).entity(ew).build();  
        } 
        else if (exception instanceof NotFoundException) {
            ew = new ExceptionWrapper(Response.Status.NOT_FOUND.getStatusCode(), 
                                      Response.Status.NOT_FOUND.getReasonPhrase(), 
                                      exception.getMessage(),
                                      "");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } 
        else if (exception instanceof NotAllowedException) {
            ew = new ExceptionWrapper(Response.Status.METHOD_NOT_ALLOWED.getStatusCode(), 
                                      Response.Status.METHOD_NOT_ALLOWED.getReasonPhrase(), 
                                      "Operación de petición incorrecta", 
                                      "");
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(ew).build();
        } 
        else {
            ew = new ExceptionWrapper(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), 
                                      Response.Status.INTERNAL_SERVER_ERROR.getReasonPhrase(), 
                                      "Error interno", 
                                      "");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
}
