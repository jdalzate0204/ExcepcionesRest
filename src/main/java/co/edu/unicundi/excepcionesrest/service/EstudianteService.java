package co.edu.unicundi.excepcionesrest.service;

import co.edu.unicundi.excepcionesrest.controller.MiObjectOutputStream;
import co.edu.unicundi.excepcionesrest.info.*;
import java.io.*;
import java.util.*;
import javax.validation.ConstraintViolation;
import javax.ws.rs.*;

/**
 * Capa logica
 * @author James Daniel Alzate Rios
 * @author Paula Alejandra Guzman Cruz
 */
public class EstudianteService {
    //Creacion del objeto que contien los atributos del estudiante
    public EstudianteInfo estudiante = new EstudianteInfo();
    //Declaracion de la lista de estudiates
    public List<EstudianteInfo> listaEstudiante; 
    //Ruta del archivo
    File archivo = new File("C:/Users/acer/Documents/1. Universidad/8° Semestre (2021-2)/Linea 2/ExcepcionesRest/ArchivoEstudiantes.txt");
    //Ruta del archivo temporal
    File archivoTemp = new File("C:/Users/acer/Documents/1. Universidad/8° Semestre (2021-2)/Linea 2/ExcepcionesRest/Temporal.txt");
    //Se crea el objeto que cotiene le metodo para ingresar los atributos
    EstudianteMetodo em = new EstudianteMetodo();   
    
    /**
     * Metodo que agrega un estudiante a la lista
     * @param est
     * @throws IOException
     * @throws IllegalArgumentException 
     * @throws CloneNotSupportedException 
     */
    public void agregar(EstudianteInfo est) 
            throws IOException, IllegalArgumentException, CloneNotSupportedException {
        listaEstudiante =  new ArrayList<>();
        estudiante = null;
        List<EstudianteInfo> lista = new ArrayList<>();
        
        if (archivo.exists() && archivo.length() > 0) {
            try {
                 HashMap<String, String> errores = new HashMap();
                 
                 for (ConstraintViolation error: est.validar())
                     errores.put(error.getPropertyPath().toString(), error.getMessage());
                 
                 if (errores.size() > 0)
                     throw new IllegalArgumentException(errores.toString());
                 else {
                     //Selecciona el archivo 
                     FileOutputStream fileOS = new FileOutputStream(archivo, true);
                     //Permite escrbir en el archivo
                     MiObjectOutputStream oos = new MiObjectOutputStream(fileOS);
                     //Selecciona el archivo 
                     FileInputStream fileIS = new FileInputStream(archivo);
                     //Permite leer el archivo
                     ObjectInputStream ois = new ObjectInputStream(fileIS);    

                    try {
                        while (true) {
                            listaEstudiante = (List) ois.readObject(); 
                            //Recorre la lista estudiante 
                            for(EstudianteInfo e : listaEstudiante){
                                if(est.getCedula().equals(e.getCedula()))
                                    estudiante = e;                                    
                            } 
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        if (estudiante == null) {
                            em.datos(est);
                            //Se añade los datos a la lista
                            lista.add(est);
                            oos.writeObject(lista);
                            listaEstudiante.addAll(lista);
                            
                            //Cierra la escritura
                            ois.close();
                            //Cierra el archivo
                            fileOS.close();
                        } else
                            throw new CloneNotSupportedException("Cedula ya registrada");
                    }
                 }
            } catch (CloneNotSupportedException e) {
                throw e;
            } catch (IllegalArgumentException e) {
                throw e;
            } catch (IOException e) {
                throw new IOException();
            }
        } else {
            try {
                HashMap<String, String> errores = new HashMap();
                
                for (ConstraintViolation error: est.validar())
                    errores.put(error.getPropertyPath().toString(), error.getMessage());
                
                if (errores.size() > 0) {
                    throw new IllegalArgumentException(errores.toString());
                } else {
                    //Selecciona el archivo
                    FileOutputStream file = new FileOutputStream(archivo);
                    //Permite escrbir en el archivo
                    ObjectOutputStream oos = new ObjectOutputStream(file);
                
                    em.datos(est);
                    //Se añade los datos a la lista
                    listaEstudiante.add(est);

                    oos.writeObject(listaEstudiante);
                    //Cierra la escritura
                    oos.close();
                    //Cierra el archivo
                    file.close();
                }
            } catch (IllegalArgumentException e){
                throw e;
            }catch (IOException e) {
                throw new IOException();
            } 
        }
    }
    
    /**
     * Metodo que muestra todos los registros de estudiante 
     * @return
     * @throws NotFoundException
     * @throws IOException 
     */
    public List<EstudianteInfo> mostrar() 
            throws NotFoundException, IOException {
        //Creacion de la lisa estudiante 
        listaEstudiante = new ArrayList<>();
        
        if (archivo.exists()) {
            try {
                //Selecciona el archivo 
                FileInputStream file = new FileInputStream(archivo);
                //Permite leer el archivo
                ObjectInputStream ois = new ObjectInputStream(file);

                try {
                    while (true)
                        listaEstudiante.addAll((List) ois.readObject());
                } catch (IOException | ClassNotFoundException e) {
                    //Cierra la escritura
                    ois.close();
                    //Cierra el archivo
                    file.close();

                    if (listaEstudiante.isEmpty())
                        throw new IllegalStateException("La lista se encuentra vacia");
                    else
                        return listaEstudiante;
                }
            } catch (IllegalStateException e) {
                throw e;
            } catch (IOException e) {
                throw e;
            }
        } else
            throw new NotFoundException("Archivo no existente");
    }
    
    /**
     * Metodo que muestra la información de un estudiante en especifico
     * @param cedula
     * @return
     * @throws NotFoundException
     * @throws IOException 
     */
    public EstudianteInfo mostrarPorCedula(String cedula) 
            throws NotFoundException, IOException {
        estudiante = null;
        //Se crea la lista estudiante
        listaEstudiante = new ArrayList<>();
        
        if (archivo.exists()) {
            try {
                //Selecciona el archivo 
                FileInputStream file = new FileInputStream(archivo);
                //Permite leer el archivo
                ObjectInputStream ois = new ObjectInputStream(file);

                try {
                    while (true) {
                        listaEstudiante = (List) ois.readObject();
                        //Recorre la lista estudiante 
                        for(EstudianteInfo e : listaEstudiante){
                            if(cedula.equals(e.getCedula())){
                                estudiante = e;
                            }
                        } 
                    }
                } catch (IOException | ClassNotFoundException e) {
                    //Cierra la escritura
                    ois.close();
                    //Cierra el archivo
                    file.close();
                    
                    if (listaEstudiante.isEmpty())
                        throw new IllegalStateException("La lista se encuentra vacia");
                    else if (estudiante != null)
                        return estudiante;
                    else
                        throw new NotFoundException("Estudiante no encontrado"); 
                }    
            } catch (NotFoundException e) {
                throw e;
            } catch (IOException e) {
                throw new IOException();
            }
        } else
            throw new NotFoundException("Archivo no encontrado");
    }
    
    /**
     * Metodo que modifica la información de un estudiante en especifico
     * @param cedula
     * @param est
     * @throws NotFoundException
     * @throws IOException 
     */
    public void modificar(String cedula, EstudianteInfo est) 
            throws NotFoundException, IOException {
        //Creacion de la lista estudiante 
        listaEstudiante = new ArrayList<>();
        //Creacion de lista nueva 
        List<EstudianteInfo> lista = new ArrayList<>();
        
        if (archivo.exists()) {
            try {
                HashMap<String, String> errores = new HashMap();

                for (ConstraintViolation error: est.validar())
                    errores.put(error.getPropertyPath().toString(), error.getMessage());

                if (errores.size() > 0)
                    throw new IllegalArgumentException(errores.toString());
                else {
                   //Selecciona el archivo para leerlo
                   FileInputStream fileIS = new FileInputStream(archivo);
                   //Permite leer el archivo
                   ObjectInputStream ois = new ObjectInputStream(fileIS);
                   //Selecciona el archivo para escrbirlo
                   FileOutputStream fileOS = new FileOutputStream(archivoTemp);
                   //Permite escribir el archivo 
                   ObjectOutputStream oos = new ObjectOutputStream(fileOS);

                    try {
                        while (true) {
                            listaEstudiante = (List) ois.readObject();
                            //Recorre la lista estudiante
                            for (EstudianteInfo e : listaEstudiante) {
                                //if (cedula.equals(e.getCedula())) {
                                    //for (EstudianteInfo es : listaEstudiante) { 
                                        if (cedula.equals(e.getCedula())) {
                                            em.datos(est);
                                            lista.add(est); 
                                            oos.writeObject(lista);
                                        }
                                        if (!cedula.equals(e.getCedula()))
                                            oos.writeObject(listaEstudiante);
                                    //}
                                /*} else 
                                    throw new NotFoundException("Estudiante no encontrado");*/
                            }
                        }
                    } catch (IOException | ClassNotFoundException e) {
                        //Cierra la escritura
                        oos.close();
                        //Cierra la lectura  
                        ois.close();

                        //Elimina el archivo original 
                        archivo.delete();
                        //Renonmbra el archivo nuevo 
                        archivoTemp.renameTo(archivo);

                        if (listaEstudiante.isEmpty())
                            throw new IllegalStateException("La lista se encuentra vacia");
                    } catch (NotFoundException e) {
                        throw e; 
                    }
                }
            } catch(IllegalArgumentException e){
                throw e;
            } catch(NotFoundException e){
                throw e;
            } catch(IllegalStateException e){
                throw e;
            }  catch (IOException e) {
                throw new IOException();
            } 
         } else
            throw new NotFoundException("Archivo no existente");
    }
    
    /**
     * Metodo que elimina un estudiante en especifico con toda su información
     * @param cedula
     * @throws NotFoundException
     * @throws IOException 
     */
    public void eliminar(String cedula) 
            throws NotFoundException, IOException{
        //Creacion de la lista 
        listaEstudiante = new ArrayList<>();
        
        if (archivo.exists()) {
            try {
                //Selecciona el archivo para leerlo
                FileInputStream fileIS = new FileInputStream(archivo);
                //Permite leer el archivo
                ObjectInputStream ois = new ObjectInputStream(fileIS);
                //Selecciona el archivo para escrbirlo
                FileOutputStream fileOS = new FileOutputStream(archivoTemp);
                //Permite escribir el archivo 
                ObjectOutputStream oos = new ObjectOutputStream(fileOS); 

                try {
                    while (true) {
                        listaEstudiante = (List) ois.readObject();
                        //Recorre la lista estudiante 
                        for(EstudianteInfo e : listaEstudiante){
                            //if(cedula.equals(e.getCedula())){
                                //for (EstudianteInfo est : listaEstudiante) { 
                                    if (!cedula.equals(e.getCedula()))
                                        oos.writeObject(listaEstudiante);
                                //}
                            /*} else
                                throw new NotFoundException("Estudiante no encontrado");*/
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    //Cierra la escritura
                    oos.close();
                    //Cierra la lectura 
                    ois.close();

                    //Elimina el archivo original 
                    archivo.delete();
                    //Renonmbra el archivo nuevo 
                    archivoTemp.renameTo(archivo);  

                    if (listaEstudiante.isEmpty())
                        throw new IllegalStateException("La lista se encuentra vacia");
                }
            } catch (NotFoundException e) {
                throw e;
            } catch (IllegalStateException e) { 
                throw e;
            } catch (IOException e) {
                throw new IOException();
            }
        } else
            throw new NotFoundException("Archivo no encontrado");
    }
}