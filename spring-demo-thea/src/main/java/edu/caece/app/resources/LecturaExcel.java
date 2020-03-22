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
import edu.caece.app.domain.Funcion;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Registro;
import edu.caece.app.domain.Rol;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.FotoRepositorio;
import edu.caece.app.repository.FuncionRepositorio;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.repository.RegistroRepositorio;
import edu.caece.app.repository.RolRepositorio;
import edu.caece.app.repository.UsuarioRepositorio;

public class LecturaExcel {

  // RUTA DENTRO DEL MISMO PROYECTO
  protected String RUTA_CSV = "/src/main/resources/bd/TP-FINAL/DatosBD.xlsx";
  protected String rutaArchivo = "";

  XSSFWorkbook worbook = null;
  XSSFSheet sheet = null;

  protected int SOLAPA_USUARIOS = 0;
  protected int SOLAPA_PERSONAS = 1;
  protected int SOLAPA_REGISTROS = 2;

  HashMap<Long, Rol> roles = new HashMap<Long, Rol>();
  HashMap<Long, Funcion> funciones = new HashMap<Long, Funcion>();
  HashMap<String, Persona> personas = new HashMap<String, Persona>();
  HashMap<String, Registro> registros = new HashMap<String, Registro>();

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public void leerArchivo() throws Exception {
    try {
      log.info(Constantes.EXCEL_LECTURA_INICIO);
      String path = System.getProperty("user.dir"); // Lectura Excel
      rutaArchivo = path + RUTA_CSV;
      FileInputStream file = new FileInputStream(new File(rutaArchivo));
      worbook = new XSSFWorkbook(file);
    } catch (Exception e) {
      throw new Exception("method leerArchivo :: " + e.getMessage());
    }
  }

  public void obtenerDatosBD(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio,
      PersonaRepositorio personaRepositorio, FuncionRepositorio funcionRepositorio,
      RegistroRepositorio registroRepositorio, FotoRepositorio fotoRepositorio) throws Exception {
    log.info(Constantes.EXCEL_LECTURA);
    try {
      leerArchivo();
      guardarRoles(rolRepositorio);
      guardarFunciones(funcionRepositorio);
      obtenerUsuarios(usuarioRepositorio);
      obtenerPersonas(personaRepositorio);
      obtenerRegistros(registroRepositorio);
      obtenerFotos(personaRepositorio, fotoRepositorio);
    } catch (Exception e) {
      throw new Exception("method inicializarBD :: " + e.getMessage());
    }
  }

  public void obtenerFotos(PersonaRepositorio personaRepositorio, FotoRepositorio fotoRepositorio)
      throws Exception {
    log.info(Constantes.EXCEL_LECTURA_FOTOS);
    try {
      LecturaCarpeta lecturaCarpeta = new LecturaCarpeta();
      lecturaCarpeta.recorrerCarpetaFotos(fotoRepositorio, personas);
    } catch (Exception e) {
      throw new Exception("method obtenerFotos :: " + e.getMessage());
    }
  }

  public void obtenerUsuarios(UsuarioRepositorio usuarioRepositorio) throws Exception {
    log.info(Constantes.EXCEL_LECTURA_USUARIOS);
    try {
      sheet = worbook.getSheetAt(SOLAPA_USUARIOS);
      ArrayList<Usuario> usuarios = leerHojaUsuarios();
      guardarUsuarios(usuarioRepositorio, usuarios);
    } catch (Exception e) {
      throw new Exception("method obtenerUsuarios :: " + e.getMessage());
    }
  }

  public void obtenerPersonas(PersonaRepositorio personaRepositorio) throws Exception {
    log.info(Constantes.EXCEL_LECTURA_PERSONAS);
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_PERSONAS);
      leerHojaPersonas();
      guardarPersonas(personaRepositorio);
    } catch (Exception e) {
      throw new Exception("method obtenerPersonas :: " + e.getMessage());
    }
  }

  public void obtenerRegistros(RegistroRepositorio registroRepositorio) throws Exception {
    log.info(Constantes.EXCEL_LECTURA_REGISTROS);
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_REGISTROS);
      leerHojaRegistros();
      guardarRegistros(registroRepositorio);
    } catch (Exception e) {
      throw new Exception("method obtenerRegistros :: " + e.getMessage());
    }
  }

  public ArrayList<Usuario> leerHojaUsuarios() throws Exception {
    log.info(Constantes.EXCEL_LECTURA_USUARIOS);
    ArrayList<Usuario> usuarios = new ArrayList<Usuario>(); // Creacion de Lista de Usuarios
    try {
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      // System.out.println("Lista Usuarios");
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
          // Rol rol = roles.get(id_rol);
          // if (rol != null) {
          // usuario.addRol(rol);
          usuarios.add(usuario); // Agrego a Lista de Usuarios
          // } else {
          // throw new Exception("method leerHojaUsuarios :: No existe el rol");
          // }
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
          celda = iterador.next(); // Leo Funcion del Excel
          Long id_funcion = (long) celda.getNumericCellValue();
          Funcion funcion = funciones.get(id_funcion);
          persona.addFuncion(funcion);
          celda = iterador.next(); // Leo Celda Matricula del Excel
          persona.setMatricula(celda.getStringCellValue());
          System.out.println(persona.toString());
          personas.put(dni, persona); // Agrego a Lista de Personas
        }
      }
    } catch (Exception e) {
      throw new Exception("method leerHojaPersonas :: " + e.getMessage());
    }
  }

  public void leerHojaRegistros() throws Exception {
    registros = new HashMap<String, Registro>(); // Creacion de Lista de Personas
    try {
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      // System.out.println("Lista Personas"); // Se Recorre Cada Fila Hasta el Final
      while (rowIterator.hasNext()) {
        fila = rowIterator.next(); // Recorro Fila del Excel
        Iterator<Cell> iterador = fila.cellIterator(); // Se Obtienen celdas de fila del Excel
        Cell celda; // Se Recorre Cada Celda de la fila del Excel
        while (iterador.hasNext()) {
          Registro registro = new Registro(); // Creo Objeto Registro
          celda = iterador.next(); // Leo Celda Nombre del Excel
          registro.setNombre(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Apellido del Excel
          registro.setApellido(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda DNI del Excel
          String dni = celda.getStringCellValue();
          registro.setDni(dni);
          celda = iterador.next(); // Leo Celda Fecha Ingreso del Excel
          registro.setFechaIngreso(FuncionesUtiles.obtenerFecha(celda.getStringCellValue()));
          System.out.println(registro.toString());
          registros.put(dni, registro); // Agrego a Lista de Registros
        }
      }
    } catch (Exception e) {
      throw new Exception("method leerHojaRegistros :: " + e.getMessage());
    }
  }

  public void guardarUsuarios(UsuarioRepositorio usuarioRepositorio, ArrayList<Usuario> users)
      throws Exception {
    log.info(Constantes.BBDD_GUARDA_USUARIOS);
    try {
      for (Usuario user : users) {
        usuarioRepositorio.save(user);
      }
      usuarioRepositorio.findAll().forEach(System.out::println);
    } catch (Exception e) {
      throw new Exception("method guardarDatosUsuarios :: " + e.getMessage());
    }
  }

  public void guardarPersonas(PersonaRepositorio personRepository) throws Exception {
    try {
      log.info(Constantes.BBDD_GUARDA_PERSONAS);
      for (Persona person : personas.values()) {
        personRepository.save(person);
        System.out.println("Se guarda :: " + person.toString()); // Se Recorre Cada Fila Hasta el
                                                                 // Final
      }
      personRepository.findAll().forEach(System.out::println);
    } catch (ConstraintViolationException e) {
      throw new Exception(
          "method guardarPersonas :: ConstraintViolationException :: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("method guardarDatosPersonas :: " + e.getMessage());
    }
  }

  public void guardarRegistros(RegistroRepositorio registroRepository) throws Exception {
    log.info(Constantes.BBDD_GUARDA_REGISTROS);
    try {
      for (Registro registro : registros.values()) {
        registroRepository.save(registro);
        System.out.println("Se guarda :: " + registro.toString()); // Se Recorre Cada Fila Hasta el
                                                                   // Final
      }
    } catch (ConstraintViolationException e) {
      throw new Exception(
          "method guardarRegistros :: ConstraintViolationException :: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("method guardarRegistros :: " + e.getMessage());
    }
  }

  public void guardarRoles(RolRepositorio rolRepository) throws Exception {
    try {
      Rol rol1 = new Rol("ADMIN");
      Rol rol2 = new Rol("USER");
      Rol rol3 = new Rol("SECURITY");
      rolRepository.save(rol1);
      rolRepository.save(rol2);
      rolRepository.save(rol3);
      roles.put(1L, rol1);
      roles.put(2L, rol2);
      roles.put(3L, rol3);
    } catch (Exception e) {
      throw new Exception("method guardarRoles :: " + e.getMessage());
    }
  }

  public void guardarFunciones(FuncionRepositorio funcionRepository) throws Exception {
    try {
      Funcion funcion1 = new Funcion("Profesor");
      Funcion funcion2 = new Funcion("Alumno");
      Funcion funcion3 = new Funcion("Administrativo");
      funcionRepository.save(funcion1);
      funcionRepository.save(funcion2);
      funcionRepository.save(funcion3);
      funciones.put(1L, funcion1);
      funciones.put(2L, funcion2);
      funciones.put(3L, funcion3);
    } catch (Exception e) {
      throw new Exception("method guardarFunciones :: " + e.getMessage());
    }
  }

}
