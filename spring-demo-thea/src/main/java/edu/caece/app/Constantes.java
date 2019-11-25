package edu.caece.app;

public class Constantes {
	
  private Constantes() {

  }
  
  public static final String LOG_BBDD = "Se inicializaron los datos en la base de datos correctamente.";
   
  // ERRORES GENERICOS
  public static final String ERROR_AUTENTICACION = "Error de autenticación";
  
  //ERRORRES USUARIOS
  public static final String ERROR_USUARIO_EXISTENTE = "Usuario existente.";
  public static final String ERROR_USUARIO_INEXISTENTE = "Usuario no encontrado.";
  public static final String ERROR_USUARIO_NO_AUTORIZADO = "Usuario no autorizado";
  public static final String ERROR_USUARIO_NO_HABILITADO = "Usuario deshabilitado";  
  public static final String ERROR_PASSWORD_INVALIDA = "Contraseña inválida";
  
  // ERRORRES PERSONAS
  public static final String ERROR_PERSONA_INEXISTENTE = "Persona no encontrada.";
  public static final String ERROR_DNI_EXISTENTE = "DNI indicado existente";
  public static final String ERROR_MATRICULA_EXISTENTE = "Matricula indicada existente";
  
  //ERRORRES USUARIOS
  public static final String ERROR_FOTO_INEXISTENTE = "No se encontraron fotos.";
}
