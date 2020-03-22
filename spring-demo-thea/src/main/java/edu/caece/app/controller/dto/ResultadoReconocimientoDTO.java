package edu.caece.app.controller.dto;

public class ResultadoReconocimientoDTO {

  private String dni;

  private double nivelConfianza;

  public String getDNI() {
    return dni;
  }

  public void setDNI(String dni) {
    this.dni = dni;
  }

  public double getNivelConfianza() {
    return nivelConfianza;
  }

  public void setNivelConfianza(double nivelConfianza) {
    this.nivelConfianza = nivelConfianza;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultDto [label=" + dni + ", confidence=" + nivelConfianza + "]";
  }
}
