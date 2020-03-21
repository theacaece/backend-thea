package edu.caece.app;

public class Constantes {

  private Constantes() {

  }

  public static final String LOG_BIENVENIDA = "Bienvenide a la facultad";
  public static final String LOG_ACCESO_PEDIDO = "Acceso %s para %s (%s)";
  public static final String LOG_ACCESO_DETECTADO =
      "Se detecto una persona, pero con una confianza menor al %s% : %f";
  public static final String LOG_ACCESO_NOCONFIABLE =
      "Acceso no autorizado, confianza menor al 60% para %s (%s)";
  public static final String LOG_ACCESO_NOENCONTRADA =
      "Se reconocio a la persona, pero no se la encontro en la base de datos con el DNI : %s";

  public static final String LOG_ACCESO_AUTORIZADO = "Autorizado";
  public static final String LOG_ACCESO_DENEGADO = "Denegado";

  public static final String EXCEL_LECTURA_INICIO = "Inicio Lectura de Datos en Excel";
  public static final String EXCEL_LECTURA = "Lectura datos en Excel";
  public static final String EXCEL_LECTURA_FOTOS = "Lectura Fotos en Excel";
  public static final String EXCEL_LECTURA_USUARIOS = "Lectura Usuarios en Excel";
  public static final String EXCEL_LECTURA_PERSONAS = "Lectura Personas en Excel";
  public static final String EXCEL_LECTURA_REGISTROS = "Lectura Personas en Excel";

  public static final String BBDD_GUARDA_USUARIOS = "Guarda Usuarios en BBDD";
  public static final String BBDD_GUARDA_PERSONAS = "Guarda Personas en BBDD";
  public static final String BBDD_GUARDA_REGISTROS = "Guarda Registros en BBDD";
  public static final String BBDD_GUARDA_FOTOS = "Guarda Fotos en BBDD";

  public static final String INFO_TOKEN = "TOKEN ...";
  public static final String INFO_AUTENTICACION = "AUTENTICACION ...";
  public static final String INFO_RECONOCIMIENTO = "RECONOCIMIENTO ...";

  public static final String INFO_ACCESO_ALL = "OBTIENE ACCESOS";
  public static final String INFO_ACCESO_ONE = "OBTIENE ACCESO POR ID";
  public static final String INFO_ACCESO_SAVE = "AGREGA ACCESO";
  public static final String INFO_ACCESO_UPDATE = "MODIFICA ACCESO";
  public static final String INFO_ACCESO_DELETE = "ELIMINA ACCESO";

  public static final String INFO_USUARIO_ALL = "OBTIENE USUARIOS";
  public static final String INFO_USUARIO_ONE = "OBTIENE USUARIO POR ID";
  public static final String INFO_USUARIO_SAVE = "AGREGA USUARIO";
  public static final String INFO_USUARIO_UPDATE = "MODIFICA USUARIO";
  public static final String INFO_USUARIO_DELETE = "ELIMINA USUARIO";

  public static final String INFO_PERSONA_ALL = "OBTIENE PERSONAS";
  public static final String INFO_PERSONA_ONE = "OBTIENE PERSONA POR ID";
  public static final String INFO_PERSONA_SAVE = "AGREGA PERSONA";
  public static final String INFO_PERSONA_UPDATE = "MODIFICA PERSONA";
  public static final String INFO_PERSONA_DELETE = "ELIMINA PERSONA";

  public static final String INFO_FOTO_ALL = "OBTIENE FOTOS";
  public static final String INFO_FOTO_ONE = "AGREGA FOTO POR ID";

  public static final String INFO_REGISTRO_ALL = "OBTIENE REGISTROS";
  public static final String INFO_REGISTRO_SAVE = "AGREGA REGISTRO";

  // ERRORES GENERICOS
  public static final String ERROR_PERSONA_NORECONOCIDA = "La persona no fue reconocida";
  public static final String ERROR_IMAGEN_INVALIDA = "La imagen es invalida";
  public static final String ERROR_AUTENTICACION = "Error de autenticación";
  public static final String ERROR_USUARIO_EXISTENTE = "Usuario existente.";
  public static final String ERROR_USUARIO_INEXISTENTE = "Usuario no encontrado.";
  public static final String ERROR_USUARIO_NO_AUTORIZADO = "Usuario no autorizado";
  public static final String ERROR_USUARIO_NO_HABILITADO = "Usuario deshabilitado";
  public static final String ERROR_PASSWORD_INVALIDA = "Contraseña inválida";
  public static final String INFO_PERSONA_EXISTENTE = "Persona encontrada.";
  public static final String INFO_DNI_EXISTENTE = "DNI indicado encontrado.";
  public static final String ERROR_PERSONA_INEXISTENTE = "Persona no encontrada.";
  public static final String ERROR_DNI_INEXISTENTE = "DNI indicado inexistente";
  public static final String ERROR_MATRICULA_EXISTENTE = "Matricula indicada existente";
  public static final String ERROR_FOTO_INEXISTENTE = "No se encontraron fotos.";

  public static final String SEPARADOR = "##############################";

}
