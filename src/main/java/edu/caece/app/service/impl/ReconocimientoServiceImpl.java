package edu.caece.app.service.impl;

import java.net.URI;
import java.util.List;
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

import edu.caece.app.domain.Person;
import edu.caece.app.repository.PersonRepository;
import edu.caece.app.service.ReconocimientoService;
import edu.caece.app.service.impl.dto.ReconocimientoResultDto;
import edu.caece.app.service.impl.dto.ReconocimientoResultsDto;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {
	
	private static final Logger logger = LoggerFactory.getLogger(ReconocimientoServiceImpl.class);
	
	@Autowired
	private PersonRepository personRepository;
	
	@Override
	public Person reconocer(byte[] imagenCara) {
		RestTemplate restTemplate = new RestTemplate();
		
		BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create("http://localhost:8085/reconocedor/matias"));
		RequestEntity<byte[]> request = requestBuilder
			.accept(MediaType.APPLICATION_JSON)
			.contentType(MediaType.APPLICATION_OCTET_STREAM)
			.contentLength(imagenCara.length)
			.<byte[]>body(imagenCara);
		
		ResponseEntity<ReconocimientoResultsDto> result = restTemplate.exchange(request, ReconocimientoResultsDto.class);
		
		Optional<ReconocimientoResultDto> findFirst = result.getBody().getResults().stream().max((a, b) -> a.getConfidence() >= b.getConfidence() ?1 : -1);
		if (findFirst.isPresent()) {
			ReconocimientoResultDto dto = findFirst.get();
			if (dto.getConfidence() > 60D) {
				String dni = dto.getLabel();
				return personRepository.findByDni(dni).orElseThrow(() -> {
					String message = String.format("Se reconoció a la persona, pero no se la encontró en la base de datos con el DNI : %s",dni);
					logger.error(message);
					return new RuntimeException(message);
				});
			}else {
				logger.info(String.format("Se detectó una persona, pero con una confianza menor al 60% : %f", dto.getConfidence()));
			}
		}
		return null;
	}

}
