package co.edu.unicundi.excepcionesrest.info;

import java.io.Serializable;
import java.util.*;
import javax.validation.*;
import javax.validation.constraints.*;
import org.hibernate.validator.constraints.Range;

/**
 * Clase pojo que contiene los atributos de estudiante
 * @author James Daniel Alzate Rios
 * @author Paula Alejandra Guzman Cruz
 * @version 1.0
 * @since 23/09/2021
 */
public class EstudianteInfo implements Serializable {
    /**
     * Identificador del estudiante
     */
    @NotNull (message = "¡Se requieren cedula!")
    @Size (min = 7, max = 10, message = "¡Debe tener un tamaño entre 7 y 10 caracteres!")
    @Pattern(regexp = "^\\d+$", message = "¡Solo se admiten numeros!")
    private String cedula;
    /**
     * Nombre del estudiante
     */
    @NotNull (message = "¡Se requieren nombre!")
    @Size (min = 3, max = 20, message = "¡Debe tener un tamaño entre 3 y 20 caracteres!")
    @Pattern(regexp = "^[a-zA-Z_]+( [a-zA-Z_]+)*$", message = "¡Solo se admiten letras!")
    private String nombre;
    /**
     * Apellido del estudiante 
     */
    @NotNull (message = "¡Se requieren apellido!")
    @Size (min = 3, max = 20, message = "¡Debe tener un tamaño entre 3 y 20 caracteres!")
    @Pattern(regexp = "^[a-zA-Z_]+( [a-zA-Z_]+)*$", message = "¡Solo se admiten letras!")
    private String apellido;
    /**
     * Edad del estudiante 
     */
    @NotNull (message = "¡Se requieren edad!")
    @Range (min = 18, max = 99, message = "¡Debe estar en el rango entre 18 y 99 años!")
    private Integer edad;
    /**
     * Correo del estudiante 
     */
    @NotNull (message = "¡Se requieren correo!")
    @Pattern (regexp = "^[^@]+@[^@]+\\.[a-zA-Z]{2,}$", message = "¡Ingrese un formato valido!")
    private String correo;
    /**
     * Semestre que esta cursando el estudiante
     */
    @NotNull (message = "¡Se requieren semestre!")
    @Range (min = 1, max = 10, message = "¡Debe estar en el rango entre 1 y 10!")
    private Integer semestre;
    /**
     * Lista de materias del estudiante 
     */
    @NotNull (message = "¡Se requieren listaMaterias!")
    @Size(min = 1, max = 5, message = "¡Debe ingresar entre 1 y 5 materias!")
    private List<String> listaMaterias;
    /**
     * Lista que contiene numeros 
     */
    @NotNull (message = "¡Se requieren numeros!")
    @Size (min = 2, max = 7, message = "¡Debe ingresar entre 2 y 7 numeros!")
    private int[] numeros;

    public EstudianteInfo() {
    }
    
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    } 

    public Integer getSemestre() {
        return semestre;
    }

    public void setSemestre(Integer semestre) {
        this.semestre = semestre;
    }

    public List<String> getListaMaterias() {
        return listaMaterias;
    }

    public void setListaMaterias(List<String> listaMaterias) {
        this.listaMaterias = listaMaterias;
    }

    public int[] getNumeros() {
        return numeros;
    }

    public void setNumeros(int[] numeros) {
        this.numeros = numeros;
    }

    /**
     * Metodo que envia la intancia para validar si tiene alguna violación 
     * @return 
     */
    public Set<ConstraintViolation<EstudianteInfo>> validar(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(this);
    }
}
