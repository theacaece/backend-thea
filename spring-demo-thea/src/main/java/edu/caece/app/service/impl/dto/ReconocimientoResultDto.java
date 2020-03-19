package edu.caece.app.service.impl.dto;

public class ReconocimientoResultDto {

  private String label;

  private double confidence;

  public double getConfidence() {
    return confidence;
  }

  public void setConfidence(double confidence) {
    this.confidence = confidence;
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultDto [label=" + label + ", confidence=" + confidence + "]";
  }
}
