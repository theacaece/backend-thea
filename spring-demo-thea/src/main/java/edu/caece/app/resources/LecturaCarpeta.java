package edu.caece.app.resources;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.caece.app.domain.Foto;
import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IFotoRepositorio;

public class LecturaCarpeta {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  // RUTA EN EL MISMO PROYECTO
  protected String RUTA_CSV = "/src/main/resources/bd/TP-FINAL/Fotos";
  protected String rutaArchivo = "";

  HashMap<String, Foto> fotos = null;

  public LecturaCarpeta() {
    fotos = new HashMap<String, Foto>();
  }

  public void recorrerCarpetaFotos(IFotoRepositorio fotoRepositorio,
      HashMap<String, Persona> personas) throws Exception {
    try {
      leerCarpeta(personas);
      guardarFotos(fotoRepositorio);
    } catch (Exception e) {
      System.out.println("method recorrerCarpetaFotos :: " + e.getMessage());
    }
  }

  public void leerCarpeta(HashMap<String, Persona> personas) throws Exception {
    try {
      String path = System.getProperty("user.dir"); // Obtiene Ruta de Carpeta Con Fotos de Personas
      rutaArchivo = path + RUTA_CSV;
      File carpeta = new File(rutaArchivo);
      File[] archivos = carpeta.listFiles(); // Obtiene Carpeta Con Fotos de Personas
      if (archivos != null && archivos.length != 0) { // Verifica si esta vacio
        for (int i = 0; i < archivos.length; i++) { // Recorre Carpetas
          File archivo = archivos[i]; // Obtiene carpeta de fotos
          if (archivo.isDirectory()) { // Verifica si es directorio con matricula
            recorrerFotos(archivo, personas); // Obtiene datos de fotos
          }
        }
      }
    } catch (Exception e) {
      System.out.println("method leerCarpeta :: " + e.getMessage());
    }
  }

  public void recorrerFotos(File carpeta, HashMap<String, Persona> personas) throws Exception {
    try {
      File[] archivos = carpeta.listFiles(); // Obtiene fotos de la carpeta
      if (archivos != null && archivos.length != 0) { // Verifica si la Carpeta de Fotos esta Vacia
        for (int i = 0; i < archivos.length; i++) { // Recorre Fotos de Carpeta
          byte[] archivoBlob = Files.readAllBytes(Paths.get(archivos[i].getPath())); // Obtiene
                                                                                     // Bytes de
                                                                                     // Imagen del
                                                                                     // Archivo con
                                                                                     // la Foto
          Persona persona = personas.get(carpeta.getName());
          if (persona != null) {
            // Foto foto = new Foto(); // Crea Objeto Foto
            // foto.setPersona(persona);
            // foto.setNombreArchivo(archivos[i].getName());
            // foto.setArchivo(archivoBlob);
            // persona.addFoto(foto);
            // log.info("Cantidad :: " + fotos.size() + " :: Carpeta :: " + carpeta.getName()
            // + " :: Foto :: " + archivos[i].getName());
            // fotos.put(archivos[i].getName(), foto); // Agrego Foto a la Lista de Fotos
          } else {
            System.out.println("La persona no existe :: DNI :: " + carpeta.getName());
          }
        }
      }
    } catch (Exception e) {
      System.out.println("method recorrerFotos :: " + e.getMessage());
    }
  }


  public void guardarFotos(IFotoRepositorio fotoRepositorio) throws Exception {
    try {
      for (Foto foto : fotos.values()) {
        log.info("Foto :: " + foto.getNombreArchivo());
        fotoRepositorio.save(foto);
      }
      fotoRepositorio.findAll().forEach(System.out::println);
    } catch (ConstraintViolationException e) {
      throw new Exception(
          "method guardarFotos :: ConstraintViolationException :: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("method guardarFotos :: " + e.getMessage());
    }
  }
}
