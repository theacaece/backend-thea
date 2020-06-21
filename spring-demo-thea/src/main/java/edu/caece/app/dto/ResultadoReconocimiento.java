package edu.caece.app.dto;

public class ResultadoReconocimiento {

  private boolean reconocido = false;

  private boolean autorizado = false;

  private String nombre;

  private String mensaje;

  public ResultadoReconocimiento() {}

  public ResultadoReconocimiento(boolean reconocido, boolean autorizado, String nombre,
      String mensaje) {
    super();
    this.reconocido = reconocido;
    this.autorizado = autorizado;
    this.nombre = nombre;
    this.mensaje = mensaje;
  }

  public boolean esReconocido() {
    return reconocido;
  }

  public void setReconocido(boolean recognized) {
    this.reconocido = recognized;
  }

  public boolean esAutorizado() {
    return autorizado;
  }

  public void setAutorizado(boolean autorizado) {
    this.autorizado = autorizado;
  }

  public String getNombre() {
    return nombre;
  }

  public void setNombre(String name) {
    this.nombre = name;
  }

  public String getMensaje() {
    return mensaje;
  }

  public void setMensaje(String message) {
    this.mensaje = message;
  }

}
