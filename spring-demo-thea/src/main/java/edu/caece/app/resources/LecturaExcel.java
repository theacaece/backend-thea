package edu.caece.app.resources;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import edu.caece.app.Constantes;
import edu.caece.app.config.Hash;
import edu.caece.app.domain.Ingreso;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Rol;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.repository.RolRepositorio;
import edu.caece.app.repository.UsuarioRepositorio;

public class LecturaExcel {

  // RUTA DENTRO DEL MISMO PROYECTO
  protected String RUTA_CSV = "/src/main/resources/bd/DatosBD.xlsx";
  protected String rutaArchivo = "";

  XSSFWorkbook worbook = null;
  XSSFSheet sheet = null;

  protected int SOLAPA_USUARIOS = 0;
  protected int SOLAPA_PERSONAS = 1;
  protected int SOLAPA_REGISTROS = 2;

  HashMap<Long, Rol> roles = new HashMap<Long, Rol>();
  HashMap<String, Persona> personas = new HashMap<String, Persona>();
  HashMap<String, Ingreso> registros = new HashMap<String, Ingreso>();

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public void leerArchivo() throws Exception {
    try {
      String path = System.getProperty("user.dir"); // Lectura Excel
      rutaArchivo = path + RUTA_CSV;
      FileInputStream file = new FileInputStream(new File(rutaArchivo));
      worbook = new XSSFWorkbook(file);
    } catch (Exception e) {
      throw new Exception("method leerArchivo :: " + e.getMessage());
    }
  }

  public void obtenerDatosBD(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio,
      PersonaRepositorio personaRepositorio) throws Exception {
    try {
      leerArchivo();
      guardarRoles(rolRepositorio);
      obtenerUsuarios(usuarioRepositorio);
      obtenerPersonas(personaRepositorio);
    } catch (Exception e) {
      throw new Exception("method inicializarBD :: " + e.getMessage());
    }
  }

  public void obtenerUsuarios(UsuarioRepositorio usuarioRepositorio) throws Exception {
    try {
      sheet = worbook.getSheetAt(SOLAPA_USUARIOS);
      ArrayList<Usuario> usuarios = leerHojaUsuarios();
      guardarUsuarios(usuarioRepositorio, usuarios);
    } catch (Exception e) {
      throw new Exception("method obtenerUsuarios :: " + e.getMessage());
    }
  }

  public void obtenerPersonas(PersonaRepositorio personaRepositorio) throws Exception {
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_PERSONAS);
      leerHojaPersonas();
      guardarPersonas(personaRepositorio);
    } catch (Exception e) {
      throw new Exception("method obtenerPersonas :: " + e.getMessage());
    }
  }

  public ArrayList<Usuario> leerHojaUsuarios() throws Exception {
    ArrayList<Usuario> usuarios = new ArrayList<Usuario>(); // Creacion de Lista de Usuarios
    try {
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      while (rowIterator.hasNext()) { // Se Recorre Cada Fila Hasta el Final
        fila = rowIterator.next(); // Recorro Fila del Excel
        Iterator<Cell> iterador = fila.cellIterator(); // Se Obtienen celdas de fila del Excel
        Cell celda; // Se Recorre Cada Celda de la fila del Excel

        while (iterador.hasNext()) {
          Usuario usuario = new Usuario(); // Creo Objeto Usuario
          celda = iterador.next(); // Leo Celda Nombre del Excel
          usuario.setFirstname(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Apellido del Excel
          usuario.setLastname(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Email del Excel
          usuario.setEmail(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Usuario del Excel
          usuario.setUsername(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Password del Excel
          usuario.setPassword(Hash.sha1(celda.getStringCellValue()));
          celda = iterador.next();// Leo Celda Rol del Excel
          Long id_rol = (long) celda.getNumericCellValue();
          Rol rol = roles.get(id_rol);
          if (rol != null) {
            usuario.addRol(rol);
            usuarios.add(usuario); // Agrego a Lista de Usuarios
          } else {
            throw new Exception("method leerHojaUsuarios :: No existe el rol");
          }
        }
      }
    } catch (Exception e) {
      throw new Exception("method leerHojaUsuarios :: " + e.getMessage());
    }
    return usuarios;
  }

  public void leerHojaPersonas() throws Exception {
    try {
      personas = new HashMap<String, Persona>(); // Creacion de Lista de Personas
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      while (rowIterator.hasNext()) {
        fila = rowIterator.next(); // Recorro Fila del Excel
        Iterator<Cell> iterador = fila.cellIterator(); // Se Obtienen celdas de fila del Excel
        Cell celda; // Se Recorre Cada Celda de la fila del Excel
        while (iterador.hasNext()) {
          Persona persona = new Persona(); // Creo Objeto Persona
          celda = iterador.next(); // Leo Celda Nombre del Excel
          persona.setNombre(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Apellido del Excel
          persona.setApellido(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda DNI del Excel
          String dni = celda.getStringCellValue();
          persona.setDni(dni);
          celda = iterador.next(); // Leo Celda Matricula del Excel
          persona.setMatricula(celda.getStringCellValue());
          personas.put(dni, persona); // Agrego a Lista de Personas
        }
      }
    } catch (Exception e) {
      throw new Exception("method leerHojaPersonas :: " + e.getMessage());
    }
  }

  public void guardarUsuarios(UsuarioRepositorio usuarioRepositorio, ArrayList<Usuario> usuarios)
      throws Exception {
    try {
      for (Usuario usuario : usuarios) {
        usuarioRepositorio.save(usuario);
      }
      if (Constantes.DEBUG) {
        usuarioRepositorio.findAll().forEach(System.out::println);
      } else {
        log.info(Constantes.BBDD_GUARDA_USUARIOS + usuarioRepositorio.findAll().size());
      }
    } catch (Exception e) {
      throw new Exception("method guardarDatosUsuarios :: " + e.getMessage());
    }
  }

  public void guardarPersonas(PersonaRepositorio personaRepositorio) throws Exception {
    try {
      for (Persona person : personas.values()) {
        personaRepositorio.save(person);
      }
      if (Constantes.DEBUG) {
        personaRepositorio.findAll().forEach(System.out::println);
      } else {
        log.info(Constantes.BBDD_GUARDA_PERSONAS + personaRepositorio.findAll().size());
      }
    } catch (ConstraintViolationException e) {
      throw new Exception(
          "method guardarPersonas :: ConstraintViolationException :: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("method guardarDatosPersonas :: " + e.getMessage());
    }
  }

  public void guardarRoles(RolRepositorio rolRepositorio) throws Exception {
    try {
      Rol rol1 = new Rol("ADMIN");
      Rol rol2 = new Rol("USER");
      Rol rol3 = new Rol("SECURITY");
      rolRepositorio.save(rol1);
      rolRepositorio.save(rol2);
      rolRepositorio.save(rol3);
      roles.put(1L, rol1);
      roles.put(2L, rol2);
      roles.put(3L, rol3);
    } catch (Exception e) {
      throw new Exception("method guardarRoles :: " + e.getMessage());
    }
  }

}
