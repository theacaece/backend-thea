package edu.caece.app.resources;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import edu.caece.app.domain.Foto;
import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IFotoRepositorio;

public class LecturaCarpeta {
  	
	protected String RUTA_CSV = "/src/main/resources/bd/TP-FINAL/Fotos"; // RUTA DENTRO DEL MISMO PROYECTO
	protected String rutaArchivo = "";
	
	HashMap<String, Foto> fotos = null;
	
	@SuppressWarnings("finally")
	public HashMap<String, Foto> recorrerCarpetaFotos(IFotoRepositorio fotoRepositorio,
													  HashMap<String, Persona> personas) throws Exception {
	  fotos = new HashMap<String, Foto>();
	  	try {
	  		String path = System.getProperty("user.dir"); // Obtiene Ruta de Carpeta Con Fotos de Personas
			rutaArchivo = path + RUTA_CSV;
		    File carpeta = new File(rutaArchivo);
		    File[] archivos = carpeta.listFiles(); // Obtiene Carpeta Con Fotos de Personas
		    if (archivos != null && archivos.length != 0) { // Verifica si esta vacio
		    	for (int i=0; i< archivos.length; i++) { // Recorre Carpetas
		    		File archivo = archivos[i]; // Obtiene carpeta de fotos
		        	if (archivo.isDirectory()) { // Verifica si es directorio con matricula
		        		fotos = recorrerFotos(archivo, personas); // Obtiene datos de fotos
		        	}
		    	}
		    }
		    guardarFotos(fotoRepositorio, fotos);
		} catch (Exception e) {
			System.out.println("method recorrerCarpetaFotos :: " + e.getMessage());
		} finally {
			return fotos;
		}
	}

	@SuppressWarnings("finally")
	public HashMap<String, Foto> recorrerFotos(File carpeta,
											   HashMap<String, Persona> personas) throws Exception {
	  try {
		  File[] archivos = carpeta.listFiles(); // Obtiene fotos de la carpeta 
		  if (archivos != null && archivos.length != 0) { // Verifica si la Carpeta de Fotos esta Vacia
			  for (int i=0; i< archivos.length; i++) { // Recorre Fotos de Carpeta
				  byte[] archivoBlob = Files.readAllBytes(Paths.get(archivos[i].getPath())); // Obtiene Bytes de Imagen del Archivo con la Foto
				  Foto foto = new Foto(); // Crea Objeto Foto
				  Persona persona = personas.get(carpeta.getName());
				  foto.setPersona(persona);
		    	  foto.setNombreArchivo(archivos[i].getName());
		    	  foto.setArchivo(archivoBlob);
		    	  System.out.println(foto.toString());
		    	  fotos.put(archivos[i].getName(), foto); // Agrego Foto a la Lista de Fotos
			  }
		    }
		} catch (Exception e) {
			System.out.println("method recorrerFotos :: " + e.getMessage());
		} finally {
			return fotos;
		}
	}
	
	
	public void guardarFotos(IFotoRepositorio fotoRepositorio,
							 HashMap<String, Foto> fotos) throws Exception {
		try {
			for (Foto foto: fotos.values()) {
				fotoRepositorio.save(foto);
			}
			fotoRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception ("method guardarFotos :: " + e.getMessage());
		}
	}
} 