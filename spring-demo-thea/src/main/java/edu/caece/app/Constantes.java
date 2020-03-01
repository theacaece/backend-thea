package edu.caece.app;

public class Constantes {

  private Constantes() {

  }

  public static final String LOG_BBDD =
      "Se inicializaron los datos en la base de datos correctamente.";

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
  public static final String ERROR_PERSONA_INEXISTENTE = "Persona no encontrada.";
  public static final String ERROR_DNI_EXISTENTE = "DNI indicado existente";
  public static final String ERROR_MATRICULA_EXISTENTE = "Matricula indicada existente";

  // ERRORES USUARIOS
  public static final String ERROR_FOTO_INEXISTENTE = "No se encontraron fotos.";
}
