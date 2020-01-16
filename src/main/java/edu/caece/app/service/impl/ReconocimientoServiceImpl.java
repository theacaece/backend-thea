package edu.caece.app.service.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.caece.app.service.ReconocimientoService;
import edu.caece.app.service.impl.dto.ReconocimientoResultsDto;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {
	
	@Override
	public String reconocer(byte[] imagenCara) {
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<ReconocimientoResultsDto> result = restTemplate.getForEntity("http://localhost:8085/reconocedor/matias", ReconocimientoResultsDto.class);
		return result.toString();
	}

}
