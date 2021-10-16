/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.unicundi.excepcionesrest.exception;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import javax.ws.rs.NotAllowedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.*;

/**
 *
 * @author PAULA GUZMAN
 */

@Provider
public class ExceptionHandler implements ExceptionMapper<Exception> {

   @Override
    public Response toResponse(Exception exception) {
        exception.printStackTrace();
        ExceptionWrapper ew;
        if (exception  instanceof IllegalArgumentException ) {
           ew = new ExceptionWrapper("400", "BAD_REQUEST", exception.getMessage(), "");
            return Response.status(Response.Status.BAD_REQUEST).entity(ew).build(); 
        } else if (exception instanceof NotFoundException) {
            ew = new ExceptionWrapper("404", "NOT_FOUND", exception.getMessage(), "/estudiantes/mostrar");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } else if (exception instanceof NotAllowedException) {
            ew = new ExceptionWrapper("405", "METHOD_NOT_ALLOWED", "Metodo incorrecto", "/estudiantes/mostrar");
            return Response.status(Response.Status.METHOD_NOT_ALLOWED).entity(ew).build();
        } else {
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/agregar");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
    
}
