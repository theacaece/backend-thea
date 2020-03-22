package edu.caece.app.controller.dto;

import java.util.List;

public class ResultadosReconocimientoDTO {

  private List<ResultadoReconocimientoDTO> resultados;

  public List<ResultadoReconocimientoDTO> getResultados() {
    return resultados;
  }

  public void setResultados(List<ResultadoReconocimientoDTO> resultados) {
    this.resultados = resultados;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultsDto [resultados=" + resultados + "]";
  }

}
