package edu.caece.app.dto;

public class ResultadoReconocimientoDTO {

  private String label;

  private double confidence;

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public double getConfidence() {
    return confidence;
  }

  public void setConfidence(double confidence) {
    this.confidence = confidence;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultDto [label=" + label + ", confidence=" + confidence + "]";
  }
}
