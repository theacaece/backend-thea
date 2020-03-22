package edu.caece.app.domain;

import java.io.Serializable;

public class JwtRequest implements Serializable {

  private static final long serialVersionUID = 5926468583005150707L;

  private String usuario;
  private String password;

  public JwtRequest() {

  }

  public JwtRequest(String usuario, String password) {
    this.setUsuario(usuario);
    this.setPassword(password);
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
