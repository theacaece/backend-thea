package edu.caece.app.controller.dto;

import java.util.List;

public class ResultadosReconocimientoDTO {

  private List<ResultadoReconocimientoDTO> resultados;

  public List<ResultadoReconocimientoDTO> getResults() {
    return resultados;
  }

  public void setResults(List<ResultadoReconocimientoDTO> resultados) {
    this.resultados = resultados;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultsDto [results=" + resultados + "]";
  }

}
