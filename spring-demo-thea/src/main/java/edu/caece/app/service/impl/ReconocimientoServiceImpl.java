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
import edu.caece.app.dto.ResultadoReconocimientoDTO;
import edu.caece.app.dto.ResultadosReconocimientoDTO;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.service.IngresoService;
import edu.caece.app.service.ReconocimientoService;

@Service
public class ReconocimientoServiceImpl implements ReconocimientoService {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public static final String URL = "http://localhost:8085/reconocedor/matias";
  private static final double NIVEL_CONFIANZA = 58D;

  @Autowired
  private PersonaRepositorio personaRepositorio;

  @Autowired
  private IngresoService seguridadService;

  @Override
  public Persona reconocerIngreso(byte[] imagenCara) {
    RestTemplate restTemplate = new RestTemplate();
    BodyBuilder requestBuilder = RequestEntity.method(HttpMethod.POST, URI.create(URL));
    RequestEntity<byte[]> request = requestBuilder.accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_OCTET_STREAM).contentLength(imagenCara.length)
        .<byte[]>body(imagenCara);
    ResponseEntity<ResultadosReconocimientoDTO> resultado =
        restTemplate.exchange(request, ResultadosReconocimientoDTO.class);
    log.info(resultado.toString());
    Optional<ResultadoReconocimientoDTO> personaReconocida = resultado.getBody().getResults()
        .stream().max((a, b) -> a.getConfidence() >= b.getConfidence() ? 1 : -1);
    log.info(personaReconocida.toString());
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
    log.info(personaReconocida.toString());
    String dni = personaReconocida.getLabel();
    Persona persona = personaRepositorio.findByDni(dni).orElseThrow(() -> {
      String mensaje = String.format(Constantes.LOG_ACCESO_NOBBDD, dni);
      log.error(mensaje);
      return new RuntimeException(mensaje);
    });
    if (personaReconocida.getConfidence() > NIVEL_CONFIANZA) {
      seguridadService.verificarIngreso(persona);
      return persona;
    } else {
      seguridadService.verificarIngresoUmbralNoAlcanzado(persona);
    }  
    return null;
  }

}
