package co.edu.unicundi.excepcionesrest.service;

import co.edu.unicundi.excepcionesrest.controller.MiObjectOutputStream;
import co.edu.unicundi.excepcionesrest.info.EstudianteInfo;
import co.edu.unicundi.excepcionesrest.info.EstudianteMetodo;
import java.io.*;
import java.util.*;
import javax.ws.rs.NotFoundException;

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
     */
    public void agregar(EstudianteInfo est) throws IOException{
        listaEstudiante =  new ArrayList<>();
        
        if (archivo.exists() && archivo.length() > 0) {
            try {
                //Selecciona el archivo
                FileOutputStream file = new FileOutputStream(archivo, true);
                //Permite escrbir en el archivo
                MiObjectOutputStream oos = new MiObjectOutputStream(file);
                
                em.datos(est);
                //Se añade los datos a la lista
                listaEstudiante.add(est);

                oos.writeObject(listaEstudiante);
                //Cierra la escritura
                oos.close();
                //Cierra el archivo
                file.close();      
            } catch (IOException e) {
                throw new IOException();
            }
        } else {
            try {
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
            } catch (IOException e) {
                throw new IOException();
            }
        }
    }
    
    /**
     * Metodo que muestra todos los registros de estudiante 
     * @return Listado de estudiantes
     * @throws Exception
     * @throws NotFoundException 
     */
    public List<EstudianteInfo> mostrar() throws Exception, NotFoundException {
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
                    e.printStackTrace();
                    //Cierra la escritura
                    ois.close();
                    //Cierra el archivo
                    file.close();
                    return listaEstudiante;
                }
            } catch (Exception e) {
                throw e;
            }
        } else
            throw new NotFoundException();
    }
    
    /**
     * Metodo que muestra la información de un estudiante en especifico
     * @param cedula
     * @return Datos del estudiante 
     * @throws NotFoundException
     * @throws Exception 
     */
    public EstudianteInfo mostrarPorCedula(String cedula) throws NotFoundException, Exception {
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
                    e.printStackTrace();
                    //Cierra la escritura
                    ois.close();
                    //Cierra el archivo
                    file.close();
                    
                    if (estudiante != null)
                        return estudiante;
                    else
                        throw new NotFoundException("Estudiante no encontrado");
                }           
            } catch (NotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new Exception();
            }
        } else
            throw new NotFoundException("Archivo no encontrado");
    }
    
    /**
     * Metodo que modifica la información de un estudiante en especifico
     * @param cedula
     * @param est
     * @throws NotFoundException
     * @throws Exception 
     */
    public void modificar(String cedula, EstudianteInfo est) throws NotFoundException, Exception {
        //Creacion de la lista estudiante 
        listaEstudiante = new ArrayList<>();
        //Creacion de lista nueva 
        List<EstudianteInfo> lista = new ArrayList<>();
        
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
                        for (EstudianteInfo e : listaEstudiante) {
                            if (cedula.equals(e.getCedula())) {
                                em.datos(est);
                                lista.add(est);
                                oos.writeObject(lista); 
                            } 
                            if (!cedula.equals(e.getCedula()))
                                oos.writeObject(listaEstudiante);
                        }
                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    //Cierra la escritura
                    oos.close();
                    //Cierra la lectura 
                    ois.close();
                    //Elimina el archivo original 
                    archivo.delete();
                    //Renonmbra el archivo nuevo 
                    archivoTemp.renameTo(archivo);
                }
            } catch (NotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new Exception();
            }
         } else
            throw new NotFoundException("Archivo no existente");
    }
    
    /**
     * Metodo que elimina un estudiante en especifico con toda su información
     * @param cedula
     * @throws NotFoundException
     * @throws Exception 
     */
    public void eliminar(String cedula) throws NotFoundException, Exception{
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
                        for (EstudianteInfo e : listaEstudiante) {
                            if (!cedula.equals(e.getCedula()))
                                oos.writeObject(listaEstudiante);
                        }
                    } 
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                    //Cierra la escritura
                    oos.close();
                    //Cierra la lectura 
                    ois.close();
                    //Elimina el archivo original 
                    archivo.delete();
                    //Renonmbra el archivo nuevo 
                    archivoTemp.renameTo(archivo); 
                }
            } catch (NotFoundException e) {
                throw e;
            } catch (Exception e) {
                throw new Exception();
            } 
        } else
            throw new NotFoundException("Archivo no encontrado");
    }
}
