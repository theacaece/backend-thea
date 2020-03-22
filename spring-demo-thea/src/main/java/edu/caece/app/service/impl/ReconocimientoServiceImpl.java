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
import edu.caece.app.controller.dto.ResultadoReconocimientoDTO;
import edu.caece.app.controller.dto.ResultadosReconocimientoDTO;
import edu.caece.app.domain.Persona;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.service.ReconocimientoService;
import edu.caece.app.service.SeguridadService;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {

  private static final Logger logger = LoggerFactory.getLogger(ReconocimientoServiceImpl.class);

  public static final String URL = "http://localhost:8085/reconocedor/matias";
  private static final double NIVEL_CONFIANZA = 60D;

  @Autowired
  private PersonaRepositorio personaRepositorio;

  @Autowired
  private SeguridadService seguridadService;

  @Override
  public Persona reconocerIngreso(byte[] imagenCara) {
    RestTemplate restTemplate = new RestTemplate();
    BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create(URL));
    RequestEntity<byte[]> request = requestBuilder.accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(imagenCara.length)
        .<byte[]>body(imagenCara);
    ResponseEntity<ResultadosReconocimientoDTO> result =
        restTemplate.exchange(request, ResultadosReconocimientoDTO.class);
    Optional<ResultadoReconocimientoDTO> personaReconocida = result.getBody().getResults().stream()
        .max((a, b) -> a.getNivelConfianza() >= b.getNivelConfianza() ? 1 : -1);
    if (personaReconocida.isPresent()) {
      return obtenerPersonaReconocida(personaReconocida);
    }
    return null;
  }

  /**
   * Buscar el resultado con mayor confianza
   * 
   * @param imagenCara
   * @return
   */
  public Persona obtenerPersonaReconocida(Optional<ResultadoReconocimientoDTO> dto) {
    ResultadoReconocimientoDTO personaReconocida = dto.get();
    String dni = personaReconocida.getDNI();
    Persona persona = personaRepositorio.findByDni(dni).orElseThrow(() -> {
      String mensaje = String.format(Constantes.LOG_ACCESO_NOBBDD, dni);
      logger.error(mensaje);
      return new RuntimeException(mensaje);
    });
    if (personaReconocida.getNivelConfianza() > NIVEL_CONFIANZA) {
      seguridadService.verificarIngreso(persona);
      return persona;
    } else {
      logger.info(String.format(Constantes.LOG_ACCESO_DETECTADO, NIVEL_CONFIANZA,
          personaReconocida.getNivelConfianza()));
      seguridadService.verificarIngresoThresholdNotMet(persona);
    }
    return null;
  }

}
