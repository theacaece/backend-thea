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
import edu.caece.app.config.Hash;
import edu.caece.app.domain.Funcion;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Registro;
import edu.caece.app.domain.Rol;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.IFotoRepositorio;
import edu.caece.app.repository.IFuncionRepositorio;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.repository.IRegistroRepositorio;
import edu.caece.app.repository.IRolRepositorio;
import edu.caece.app.repository.IUsuarioRepositorio;

public class LecturaExcel {

  // RUTA DENTRO DEL MISMO PROYECTO
  protected String RUTA_CSV = "/src/main/resources/bd/TP-FINAL/DatosBD.xlsx";
  protected String rutaArchivo = "";

  XSSFWorkbook worbook = null;
  XSSFSheet sheet = null;

  protected int SOLAPA_ROLES = 0;
  protected int SOLAPA_FUNCIONES = 1;
  protected int SOLAPA_USUARIOS = 2;
  protected int SOLAPA_PERSONAS = 3;
  protected int SOLAPA_REGISTROS = 3;

  HashMap<Long, Rol> roles = new HashMap<Long, Rol>();
  HashMap<Long, Funcion> funciones = new HashMap<Long, Funcion>();
  HashMap<String, Persona> personas = new HashMap<String, Persona>();
  HashMap<String, Registro> registros = new HashMap<String, Registro>();

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public void leerArchivo() {
    try {
      String path = System.getProperty("user.dir"); // Lectura Excel
      rutaArchivo = path + RUTA_CSV;
      FileInputStream file = new FileInputStream(new File(rutaArchivo));
      worbook = new XSSFWorkbook(file);
    } catch (Exception e) {
      System.out.println("method leerArchivo :: " + e.getMessage());
    }
  }

  public void inicializarBD(IUsuarioRepositorio usuarioRepositorio, IRolRepositorio rolRepositorio,
      IPersonaRepositorio personaRepositorio, IFuncionRepositorio funcionRepositorio,
      IRegistroRepositorio registroRepositorio, IFotoRepositorio fotoRepositorio) {
    try {
      obtenerRoles(rolRepositorio);
      obtenerUsuarios(usuarioRepositorio);
      obtenerFunciones(funcionRepositorio);
      obtenerPersonas(personaRepositorio);
      obtenerRegistros(registroRepositorio);
      // obtenerFotos(personaRepositorio, fotoRepositorio);
    } catch (Exception e) {
      System.out.print("method inicializarBD :: " + e.getMessage());
    }
  }

  public void obtenerFotos(IPersonaRepositorio personaRepositorio,
      IFotoRepositorio fotoRepositorio) {
    try {
      LecturaCarpeta lecturaCarpeta = new LecturaCarpeta();
      lecturaCarpeta.recorrerCarpetaFotos(fotoRepositorio, personas);
    } catch (Exception e) {
      System.out.print("method obtenerFotos :: " + e.getMessage());
    }
  }

  public void obtenerRoles(IRolRepositorio rolRepositorio) {
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_ROLES);
      leerHojaRoles();
      guardarRoles(rolRepositorio);
    } catch (Exception e) {
      System.out.println("method obtenerRoles :: " + e.getMessage());
    }
  }

  @SuppressWarnings("finally")
  public ArrayList<Usuario> obtenerUsuarios(IUsuarioRepositorio usuarioRepositorio) {
    ArrayList<Usuario> usuarios = null;
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_USUARIOS);
      usuarios = leerHojaUsuarios();
      guardarUsuarios(usuarioRepositorio, usuarios);
    } catch (Exception e) {
      System.out.print("method obtenerUsuarios :: " + e.getMessage());
    } finally {
      return usuarios;
    }
  }

  public void obtenerFunciones(IFuncionRepositorio funcionRepositorio) {
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_FUNCIONES);
      leerHojaFunciones();
      guardarFunciones(funcionRepositorio);
    } catch (Exception e) {
      System.out.println("method obtenerFunciones :: " + e.getMessage());
    }
  }

  @SuppressWarnings("finally")
  public ArrayList<Persona> obtenerPersonas(IPersonaRepositorio personaRepositorio) {
    ArrayList<Persona> personas = null;
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_PERSONAS);
      leerHojaPersonas();
      guardarPersonas(personaRepositorio);
    } catch (Exception e) {
      System.out.println("method obtenerPersonas :: " + e.getMessage());
    } finally {
      return personas;
    }
  }

  @SuppressWarnings("finally")
  public ArrayList<Registro> obtenerRegistros(IRegistroRepositorio registroRepositorio) {
    ArrayList<Registro> registros = null;
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_REGISTROS);
      leerHojaRegistros();
      guardarRegistros(registroRepositorio);
    } catch (Exception e) {
      System.out.println("method obtenerRegistros :: " + e.getMessage());
    } finally {
      return registros;
    }
  }

  @SuppressWarnings("finally")
  public ArrayList<Persona> probar() {
    ArrayList<Persona> personas = null;
    try {
      leerArchivo();
      sheet = worbook.getSheetAt(SOLAPA_PERSONAS);
      leerHojaPersonas();
      // guardarPersonas(personaRepositorio);
    } catch (Exception e) {
      System.out.println("method probar :: " + e.getMessage());
    } finally {
      return personas;
    }
  }

  public ArrayList<Usuario> leerHojaUsuarios() {
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
          Rol rol = roles.get(id_rol);
          usuario.addRol(rol);
          usuarios.add(usuario); // Agrego a Lista de Usuarios
        }
      }
    } catch (Exception e) {
      System.out.println("method leerHojaUsuarios :: " + e.getMessage());
    }
    return usuarios;
  }

  public void leerHojaRoles() {
    try {
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      // System.out.println("Lista Roles"); // Se Recorre Cada Fila Hasta el Final
      while (rowIterator.hasNext()) {
        fila = rowIterator.next(); // Recorro Fila del Excel
        Iterator<Cell> iterador = fila.cellIterator(); // Se Obtienen celdas de fila del Excel
        Cell celda;
        while (iterador.hasNext()) { // Se Recorre Cada Celda de la fila del Excel
          celda = iterador.next(); // Leo Celda Id Rol
          Long id = (long) celda.getNumericCellValue();
          celda = iterador.next(); // Leo Celda Nombre Rol
          String nombre = celda.getStringCellValue();
          Rol rol = new Rol(id, nombre);
          roles.put(rol.getId(), rol); // Agrego a Lista de Usuarios
        }
      }
    } catch (Exception e) {
      System.out.print("method leerHojaRoles :: " + e.getMessage());
    }
  }

  public void leerHojaPersonas() {
    try {
      personas = new HashMap<String, Persona>(); // Creacion de Lista de Personas
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      // System.out.println("Lista Personas"); // Se Recorre Cada Fila Hasta el Final
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
      System.out.print("method leerHojaPersonas :: " + e.getMessage());
    }
  }

  public void leerHojaRegistros() {
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
          Registro registro = new Registro(); // Creo Objeto Persona
          celda = iterador.next(); // Leo Celda Nombre del Excel
          registro.setNombre(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda Apellido del Excel
          registro.setApellido(celda.getStringCellValue());
          celda = iterador.next(); // Leo Celda DNI del Excel
          String dni = celda.getStringCellValue();
          registro.setDni(dni);
          celda = iterador.next();
          celda = iterador.next(); // Leo Celda Matricula del Excel
          registro.setMatricula(celda.getStringCellValue());

          System.out.println(registro.toString());

          registros.put(dni, registro); // Agrego a Lista de Registros
        }
      }
    } catch (Exception e) {
      System.out.print("method leerHojaPersonas :: " + e.getMessage());
    }
  }

  public void leerHojaFunciones() throws Exception {
    try {
      Iterator<Row> rowIterator = sheet.iterator(); // Obtiene Todas las Filas de Excel
      Row fila;
      rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos
      // System.out.println("Lista Funciones"); // Se Recorre Cada Fila Hasta el Final
      while (rowIterator.hasNext()) {
        fila = rowIterator.next(); // Recorro Fila del Excel
        Iterator<Cell> iterador = fila.cellIterator(); // Se Obtienen celdas de fila del Excel
        Cell celda; // Se Recorre Cada Celda de la fila del Excel
        while (iterador.hasNext()) {
          celda = iterador.next(); // Leo Celda Id Funcion
          Long id = (long) celda.getNumericCellValue();
          celda = iterador.next(); // Leo Celda Nombre Funcion
          String nombre = celda.getStringCellValue();
          Funcion funcion = new Funcion(id, nombre);
          funciones.put(id, funcion); // Agrego a Lista de Usuarios
        }
      }
    } catch (Exception e) {
      throw new Exception("method leerHojaFunciones :: " + e.getMessage());
    }
  }

  public void guardarUsuarios(IUsuarioRepositorio usuarioRepositorio, ArrayList<Usuario> users)
      throws Exception {
    try {
      for (Usuario user : users) {
        usuarioRepositorio.save(user);
      }
      usuarioRepositorio.findAll().forEach(System.out::println);
    } catch (Exception e) {
      throw new Exception("method guardarDatosUsuarios :: " + e.getMessage());
    }
  }

  public void guardarRoles(IRolRepositorio rolRepositorio) throws Exception {
    try {
      for (Rol rol : roles.values()) {
        rolRepositorio.save(rol);
      }
      rolRepositorio.findAll().forEach(System.out::println);
    } catch (Exception e) {
      throw new Exception("method guardarDatosRoles :: " + e.getMessage());
    }
  }

  public void guardarFunciones(IFuncionRepositorio funcionRepositorio) throws Exception {
    try {
      for (Funcion funcion : funciones.values()) {
        funcionRepositorio.save(funcion);
      }
      funcionRepositorio.findAll().forEach(System.out::println);
    } catch (Exception e) {
      throw new Exception("method guardarFunciones :: " + e.getMessage());
    }
  }

  public void guardarPersonas(IPersonaRepositorio personRepository) throws Exception {
    try {
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

  public void guardarRegistros(IRegistroRepositorio registroRepository) throws Exception {
    System.out.println("Inicio guardarRegistros()"); // Se Recorre Cada Fila Hasta el Final
    try {
      for (Registro registro : registros.values()) {
        registroRepository.save(registro);
        System.out.println("Se guarda :: " + registro.toString()); // Se Recorre Cada Fila Hasta el
                                                                   // Final
      }
      // registroRepository.findAll().forEach(System.out::println);
    } catch (ConstraintViolationException e) {
      throw new Exception(
          "method guardarRegistros :: ConstraintViolationException :: " + e.getMessage());
    } catch (Exception e) {
      throw new Exception("method guardarRegistros :: " + e.getMessage());
    }
  }

}
