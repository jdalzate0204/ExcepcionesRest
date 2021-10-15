package co.edu.unicundi.excepcionesrest.controller;

import co.edu.unicundi.excepcionesrest.exception.ExceptionWrapper;
import co.edu.unicundi.excepcionesrest.info.EstudianteInfo;
import co.edu.unicundi.excepcionesrest.service.EstudianteService;
import java.io.*;
import java.util.*;
import javax.ejb.Stateless;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.*;
import javax.ws.rs.*;
import javax.ws.rs.core.*; 

/**
 * Clase controlador de los servicios
 * @author James Daniel Alzate Rios
 * @author Paula Alejandra Guzman Cruz
 * @version 1.0
 * @since 23/09/2021
 */
@Stateless
@Path("/estudiantes")
public class EstudianteController {     
    EstudianteService es = new EstudianteService();
    ExceptionWrapper ew;
    
    /**
     * Rest que llama al metodo que agrega un estudiante a la lista 
     * @param est recibe la informacion del estudiante
     * @return Mensaje de confirmacion
     */
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(EstudianteInfo est) {
        try {
             HashMap<String, String> errores = new HashMap();
                
             for (ConstraintViolation error: est.validar())
                 errores.put(error.getPropertyPath().toString(), error.getMessage());
                
             if (errores.size() > 0) {
                 ew = new ExceptionWrapper("400", "BAD_REQUEST", "Error de validaciones de campo: " + errores.toString(), "/estudiantes/agregar");
                 return Response.status(Response.Status.BAD_REQUEST).entity(ew).build();
             } else {
                 es.agregar(est);
                 return Response.status(Response.Status.CREATED).entity(est.getNombre() + " Registrado Exitosamente").build();
             }
        } catch (IOException e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/agregar");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
    
    /**
     * Rest que llama al metodo que muestra todos los registros de estudiante 
     * @return un alista de estudiantes 
     */
    @GET
    @Path("/mostrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrar(){
        try {
            es.mostrar();
            return Response.status(Response.Status.OK).entity(es.listaEstudiante).build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("404", "NOT_FOUND", "Archivo no existente", "/estudiantes/mostrar");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } catch (Exception e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/mostrar");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        } 
    }
    
    /**
     * Rest que llama al metodo que muestra la información de un estudiante en especifico
     * @param cedula recibe el id del estudiante 
     * @return 
     */
    @GET
    @Path("/mostrarPorCedula/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarPorCedula(@NotNull (message = "¡Se requieren cedula!")
                                     @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
                                     @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
                                     @PathParam("cedula") String cedula){
        
        try {
            es.mostrarPorCedula(cedula);
            return Response.status(Response.Status.OK).entity(es.estudiante).build();
        } catch (NotFoundException e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("404", "NOT_FOUND", e.getMessage(), "/estudiantes/mostrar/{cedula}");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } catch (Exception e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/mostrar/{cedula}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
    
    /**
     * Rest que llama al metodo que modifica la información de un estudiante en especifico
     * @param cedula recibe el identificador del estudiante
     * @param est recibe los datos que desea modificar 
     * @return 
     */
    @PUT 
    @Path("/modificarPorCedula/{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(@NotNull (message = "¡Se requieren cedula!")
                              @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
                              @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
                              @PathParam("cedula") String cedula, EstudianteInfo est){
        
        try {
            HashMap<String, String> errores = new HashMap();
                
            for (ConstraintViolation error: est.validar())
                errores.put(error.getPropertyPath().toString(), error.getMessage());
            
            if (errores.size() > 0) {
                ew = new ExceptionWrapper("400", "BAD_REQUEST", "Error de validaciones de campo: " + errores.toString(), "/estudiantes/agregar");
                return Response.status(Response.Status.BAD_REQUEST).entity(ew).build();
            } else {
                es.modificar(cedula, est);
                return Response.status(Response.Status.OK).entity("Registro de " + est.getNombre() + " Modificado Exitosamente").build();
            }
        } catch (NotFoundException e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("404", "NOT_FOUND", e.getMessage(), "/estudiantes/modificarPorCedula/{cedula}");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } catch (Exception e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/modificarPorCedula/{cedula}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
    
    /**
     * Rest que llama al metodo que elimina un estudiante en especifico con toda su información
     * @param cedula recibe el identificador del estudiante
     * @return 
     */
    @DELETE
    @Path("/eliminarPorCedula/{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response eliminar(@NotNull (message = "¡Se requieren cedula!")
                             @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
                             @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
                             @PathParam("cedula") String cedula){
        
        try {
            es.eliminar(cedula);
            return Response.status(Response.Status.NO_CONTENT).build();
        } catch (NotFoundException e){
            e.printStackTrace();
            ew = new ExceptionWrapper("404", "NOT_FOUND", e.getMessage(), "/estudiantes/eliminarPorCedula/{cedula}");
            return Response.status(Response.Status.NOT_FOUND).entity(ew).build();
        } catch (Exception e) {
            e.printStackTrace();
            ew = new ExceptionWrapper("500", "INTERNAL_SERVER_ERROR", "Error interno", "/estudiantes/eliminarPorCedula/{cedula}");
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ew).build();
        }
    }
}