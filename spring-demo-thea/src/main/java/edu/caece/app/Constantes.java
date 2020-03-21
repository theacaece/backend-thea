package edu.caece.app;

public class Constantes {

  private Constantes() {

  }

  public static final String SEPARADOR = "##############################";

  public static final String EXCEL_INICIO = "Inicio Lectura de Datos en Excel";
  public static final String EXCEL_LECTURA = "Lectura datos en Excel";
  public static final String EXCEL_LECTURA_FOTOS = "Lectura Fotos en Excel";
  public static final String EXCEL_LECTURA_USUARIOS = "Lectura Usuarios en Excel";
  public static final String EXCEL_LECTURA_PERSONAS = "Lectura Personas en Excel";
  public static final String EXCEL_LECTURA_REGISTROS = "Lectura Personas en Excel";

  public static final String BBDD_GUARDA_USUARIOS = "Guarda Usuarios en BBDD";
  public static final String BBDD_GUARDA_PERSONAS = "Guarda Personas en BBDD";
  public static final String BBDD_GUARDA_REGISTROS = "Guarda Registros en BBDD";

  public static final String ERROR_PERSONA_NORECONOCIDA = "La persona no fue reconocida";
  public static final String ERROR_IMAGEN_INVALIDA = "La imagen es invalida";

  public static final String INFO_REGISTRO_ALL = "OBTIENE REGISTROS";
  public static final String INFO_REGISTRO_SAVE = "AGREGA REGISTRO";

  public static final String INFO_USUARIO_ALL = "OBTIENE USUARIOS";
  public static final String INFO_USUARIO_SAVE = "AGREGA USUARIO";
  public static final String INFO_USUARIO_DELETE = "ELIMINA USUARIO";
  public static final String INFO_USUARIO_UPDATE = "MODIFICA USUARIO";

  // ERRORES GENERICOS
  public static final String ERROR_AUTENTICACION = "Error de autenticación";

  // ERRORES USUARIOS
  public static final String ERROR_USUARIO_EXISTENTE = "Usuario existente.";
  public static final String ERROR_USUARIO_INEXISTENTE = "Usuario no encontrado.";
  public static final String ERROR_USUARIO_NO_AUTORIZADO = "Usuario no autorizado";
  public static final String ERROR_USUARIO_NO_HABILITADO = "Usuario deshabilitado";
  public static final String ERROR_PASSWORD_INVALIDA = "Contraseña inválida";

  // ERRORES PERSONAS
  public static final String INFO_PERSONA_EXISTENTE = "Persona encontrada.";
  public static final String INFO_DNI_EXISTENTE = "DNI indicado encontrado.";

  public static final String ERROR_PERSONA_INEXISTENTE = "Persona no encontrada.";
  public static final String ERROR_DNI_INEXISTENTE = "DNI indicado inexistente";
  public static final String ERROR_MATRICULA_EXISTENTE = "Matricula indicada existente";

  // ERRORES USUARIOS
  public static final String ERROR_FOTO_INEXISTENTE = "No se encontraron fotos.";
}
