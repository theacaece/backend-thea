package edu.caece.app.service.impl;

import java.net.URI;

import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
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
		
		BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create("http://localhost:8085/reconocedor/matias"));
		RequestEntity<byte[]> request = requestBuilder
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.contentLength(imagenCara.length)
			.<byte[]>body(imagenCara);
		
		ResponseEntity<ReconocimientoResultsDto> result = restTemplate.exchange(request, ReconocimientoResultsDto.class);

		return result.toString();
	}

}
