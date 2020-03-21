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
import edu.caece.app.Constantes;
import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.service.ReconocimientoService;
import edu.caece.app.service.SecuridadService;
import edu.caece.app.service.impl.dto.ResultadoReconocimientoDTO;
import edu.caece.app.service.impl.dto.ResultadosReconocimientoDTO;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {

  private static final double CONFIDENCE_THRESHOLD = 60D;
  public static final String URL = "http://localhost:8085/reconocedor/matias";

  private static final Logger logger = LoggerFactory.getLogger(ReconocimientoServiceImpl.class);

  @Autowired
  private IPersonaRepositorio personaRepositorio;

  @Autowired
  private SecuridadService securidadService;

  @Override
  public Persona reconocerIngreso(byte[] imagenCara) {
    RestTemplate restTemplate = new RestTemplate();
    BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create(URL));
    RequestEntity<byte[]> request = requestBuilder.accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(imagenCara.length)
        .<byte[]>body(imagenCara);

    ResponseEntity<ResultadosReconocimientoDTO> result =
        restTemplate.exchange(request, ResultadosReconocimientoDTO.class);

    // Buscar el resutltado con mayor confianza
    Optional<ResultadoReconocimientoDTO> personaReconocida = result.getBody().getResults().stream()
        .max((a, b) -> a.getConfidence() >= b.getConfidence() ? 1 : -1);
    if (personaReconocida.isPresent()) {
      ResultadoReconocimientoDTO dto = personaReconocida.get();
      String dni = dto.getLabel();
      Persona person = personaRepositorio.findByDni(dni).orElseThrow(() -> {
        String message = String.format(Constantes.LOG_ACCESO_NOENCONTRADA, dni);
        logger.error(message);
        return new RuntimeException(message);
      });
      if (dto.getConfidence() > CONFIDENCE_THRESHOLD) {
        securidadService.logAccess(person);
        return person;
      } else {
        logger.info(String.format(Constantes.LOG_ACCESO_DETECTADO, CONFIDENCE_THRESHOLD,
            dto.getConfidence()));
        securidadService.logAccessThresholdNotMet(person);
      }
    }
    return null;
  }

}
