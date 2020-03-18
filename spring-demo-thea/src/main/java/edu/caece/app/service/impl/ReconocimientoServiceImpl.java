package edu.caece.app.service.impl;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.service.ReconocimientoService;
import edu.caece.app.service.SecurityService;
import edu.caece.app.service.impl.dto.ReconocimientoResultDto;
import edu.caece.app.service.impl.dto.ReconocimientoResultsDto;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {
	
	private static final double CONFIDENCE_THRESHOLD = 60D;

	private static final Logger logger = LoggerFactory.getLogger(ReconocimientoServiceImpl.class);
	
	@Autowired
	private IPersonaRepositorio personRepository;
	
	@Autowired
	private SecurityService securityService;
	
	@Override
	public Persona recognize(byte[] faceImageData) {
		RestTemplate restTemplate = new RestTemplate();
		
		BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create("http://localhost:8085/reconocedor/matias"));
		RequestEntity<byte[]> request = requestBuilder
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.contentLength(faceImageData.length)
			.<byte[]>body(faceImageData);
		
		ResponseEntity<ReconocimientoResultsDto> result = restTemplate.exchange(request, ReconocimientoResultsDto.class);
		
		// Buscar el resutltado con mayor confianza
		Optional<ReconocimientoResultDto> findFirst = result.getBody().getResults().stream().max((a, b) -> a.getConfidence() >= b.getConfidence() ?1 : -1);
		if (findFirst.isPresent()) {
			ReconocimientoResultDto dto = findFirst.get();
			String dni = dto.getLabel();
			Persona person = personRepository.findByDni(dni).orElseThrow(() -> {
				String message = String.format("Se reconoció a la persona, pero no se la encontró en la base de datos con el DNI : %s",dni);
				logger.error(message);
				return new RuntimeException(message);
			});
			if (dto.getConfidence() > CONFIDENCE_THRESHOLD) {
				securityService.logAccess(person);
				return person;
			}else {
				logger.info(String.format("Se detectó una persona, pero con una confianza menor al %s% : %f", CONFIDENCE_THRESHOLD, dto.getConfidence()));
				securityService.logAccessThresholdNotMet(person);
			}
		}
		return null;
	}

}
