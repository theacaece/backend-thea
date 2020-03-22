package edu.caece.app.dto;

import java.util.List;

public class ResultadosReconocimientoDTO {

  private List<ResultadoReconocimientoDTO> results;

  public List<ResultadoReconocimientoDTO> getResults() {
    return results;
  }

  public void setResults(List<ResultadoReconocimientoDTO> results) {
    this.results = results;
  }

  @Override
  public String toString() {
    return "ReconocimientoResultsDto [results=" + results + "]";
  }

}
