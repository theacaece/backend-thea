package edu.caece.app.controller.dto;

public class ResultadoReconocimientoDTO {

  private String dni;

  private double confidence;

  public double getConfidence() {
    return confidence;
  }

  public void setConfidence(double confidence) {
    this.confidence = confidence;
  }

  public String getDNI() {
    return dni;
  }

  public void setDNI(String dni) {
    this.dni = dni;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultDto [label=" + dni + ", confidence=" + confidence + "]";
  }
}
