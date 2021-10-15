package co.edu.unicundi.excepcionesrest.controller;

import co.edu.unicundi.excepcionesrest.exception.ExceptionWrapper;
import co.edu.unicundi.excepcionesrest.info.EstudianteInfo;
import co.edu.unicundi.excepcionesrest.service.EstudianteService;
import java.io.*;
import javax.ejb.Stateless;
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
     * @throws java.io.IOException
     */
    @POST
    @Path("/agregar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response agregar(EstudianteInfo est) throws IOException {
        es.agregar(est);
        return Response.status(Response.Status.CREATED).entity(est.getNombre() + " Registrado Exitosamente").build();
    }
    
    /**
     * Rest que llama al metodo que muestra todos los registros de estudiante 
     * @return un alista de estudiantes 
     * @throws java.io.IOException 
     */
    @GET
    @Path("/mostrar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrar() throws NotFoundException, IOException {
        es.mostrar();
        return Response.status(Response.Status.OK).entity(es.listaEstudiante).build();
    }
    
    /**
     * Rest que llama al metodo que muestra la información de un estudiante en especifico
     * @param cedula recibe el id del estudiante 
     * @return 
     * @throws java.io.IOException 
     */
    @GET
    @Path("/mostrarPorCedula/{cedula}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response mostrarPorCedula(@NotNull (message = "¡Se requieren cedula!")
                                     @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
                                     @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
                                     @PathParam("cedula") String cedula) throws NotFoundException, IOException{
        es.mostrarPorCedula(cedula);
        return Response.status(Response.Status.OK).entity(es.estudiante).build();
    }
    
    /**
     * Rest que llama al metodo que modifica la información de un estudiante en especifico
     * @param cedula recibe el identificador del estudiante
     * @param est recibe los datos que desea modificar 
     * @return 
     * @throws java.io.IOException 
     */
    @PUT 
    @Path("/modificarPorCedula/{cedula}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response modificar(@NotNull (message = "¡Se requieren cedula!")
                              @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
                              @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
                              @PathParam("cedula") String cedula, EstudianteInfo est) throws NotFoundException,IOException{
        es.modificar(cedula, est);
        return Response.status(Response.Status.OK).entity("Registro de " + est.getNombre() + " Modificado Exitosamente").build();
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
                             @PathParam("cedula") String cedula) throws NotFoundException,IOException{
        es.eliminar(cedula);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}