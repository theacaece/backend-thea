package edu.caece.app.resources;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import edu.caece.app.domain.Foto;
import edu.caece.app.repository.IFotoRepositorio;

public class LecturaCarpeta {
  	
	// RUTA DENTRO DEL MISMO PROYECTO
	protected String RUTA_CSV = "\\src\\main\\resources\\bd\\TP-FINAL\\Fotos";
	protected String rutaArchivo = "";
	
	ArrayList<Foto> fotos = null;
	
	@SuppressWarnings("finally")
	public ArrayList<Foto> recorrerCarpetaFotos(IFotoRepositorio fotoRepositorio) throws Exception {
	  fotos = new ArrayList<Foto>();
	  	try {
	  		String path = System.getProperty("user.dir"); // Obtiene Ruta de Carpeta Con Fotos de Personas
			rutaArchivo = path + RUTA_CSV;
		    File carpeta = new File(rutaArchivo);
		    File[] archivos = carpeta.listFiles(); // Obtiene Carpeta Con Fotos de Personas
		    if (archivos != null && archivos.length != 0) { // Verifica si esta vacio
		    	for (int i=0; i< archivos.length; i++) { // Recorre Carpetas
		    		File archivo = archivos[i]; // Obtiene carpeta de fotos
		        	if (archivo.isDirectory()) { // Verifica si es directorio con matricula
		        		fotos = recorrerFotos(archivo); // Obtiene datos de fotos
		        	}
		    	}
		    }
		    guardarFotos(fotoRepositorio, fotos);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return fotos;
		}
	}

	@SuppressWarnings("finally")
	public ArrayList<Foto> recorrerFotos(File carpeta) throws Exception {
	  try {
		  File[] archivos = carpeta.listFiles(); // Obtiene fotos de la carpeta 
		  // Verifica si la Carpeta de Fotos esta Vacia
		  if (archivos != null && archivos.length != 0) {
			  // Recorre Fotos de Carpeta
			  for (int i=0; i< archivos.length; i++) {
				  
				  // Obtiene Bytes de Imagen del Archivo con la Foto
				  byte[] archivoBlob = Files.readAllBytes(Paths.get(archivos[i].getPath()));
		    	  
				  // Crea Objeto Foto
				  Foto foto = new Foto();
		    	  foto.setDniPersona(carpeta.getName());
		    	  foto.setNombreArchivo(archivos[i].getName());
		    	  foto.setArchivo(archivoBlob);
		    	  System.out.println(foto.toString());
		    	  
		    	  // Agrego Foto a la Lista de Fotos
		    	  fotos.add(foto);
			  }
		    }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
			return fotos;
		}
	}
	
	
	public void guardarFotos(IFotoRepositorio fotoRepositorio,
							 ArrayList<Foto> fotos) throws Exception {
		try {
			for (Foto foto: fotos) {
				fotoRepositorio.save(foto);
			}
			fotoRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception ("method guardarFotos" + e.getMessage());
		}
	}
} 