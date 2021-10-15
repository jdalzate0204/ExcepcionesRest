package co.edu.unicundi.excepcionesrest.info;

/**
 * Clase que contiene la informacion del estudiante 
 * @author James Daniel Alzate Rios
 * @author Paula Alejandra Guzman Cruz
 * @version 1.0
 * @since  23/09/2021
 */
public class EstudianteMetodo {
    /**
     * Contiene los datos del estudiante
     * @param estudiante 
     */
    public void datos(EstudianteInfo estudiante){
        estudiante.getApellido();
        estudiante.getCedula();
        estudiante.getCorreo();
        estudiante.getEdad();
        estudiante.getNombre();
        estudiante.getSemestre();
        estudiante.getListaMaterias();
        estudiante.getNumeros();
    }
}