package edu.caece.app.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Persona;
import edu.caece.app.dto.ResultadoReconocimiento;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.service.ReconocimientoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ReconocedorController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private PersonaRepositorio personaRepositorio;
  @Autowired
  private ReconocimientoService reconocimientoService;

  @RequestMapping(value = "/reconocer", method = RequestMethod.POST)
  public ResultadoReconocimiento reconocer(@RequestBody byte[] captura) throws Exception {
    log.info(Constantes.INFO_RECONOCIMIENTO);
    if (this.esImagenValida(captura)) {
      Persona persona = reconocimientoService.reconocerIngreso(captura);
      if (persona != null) {
        return new ResultadoReconocimiento(true, persona.isHabilitado(),
            persona.getNombreCompleto(), Constantes.LOG_BIENVENIDA);
      }
      return new ResultadoReconocimiento(false, false, null, Constantes.ERROR_PERSONA_NORECONOCIDA);
    } else {
      log.info(Constantes.INFO_RECONOCIMIENTO);
      throw new Exception(Constantes.ERROR_IMAGEN_INVALIDA);
    }
  }

  public void registrarPersona(@RequestParam Long id, @RequestBody byte[] payload) {
  }

  private boolean esImagenValida(byte[] payload) throws Exception {
    try {
      ImageIO.read(new ByteArrayInputStream(payload));
      return true;
    } catch (IOException e) {
      log.debug("method esImagenValida :: IOException :: ", e);
    } catch (Exception e) {
      throw new Exception("method esImagenValida :: " + e.getMessage());
    }
    return false;
  }

}
