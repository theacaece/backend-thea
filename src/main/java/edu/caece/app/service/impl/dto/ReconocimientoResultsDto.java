package edu.caece.app.service.impl.dto;

import java.util.List;

public class ReconocimientoResultsDto {
	
	private List<ReconocimientoResultDto> results;

	public List<ReconocimientoResultDto> getResults() {
		return results;
	}

	public void setResults(List<ReconocimientoResultDto> results) {
		this.results = results;
	}

	@Override
	public String toString() {
		return "ReconocimientoResultsDto [results=" + results + "]";
	}
	
}
