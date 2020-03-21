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
import edu.caece.app.controller.dto.ReconocerPersonaResult;
import edu.caece.app.domain.Persona;
import edu.caece.app.service.ReconocimientoService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class ReconocedorController {

  private final static Logger log = LoggerFactory.getLogger(ReconocedorController.class);

  @Autowired
  private ReconocimientoService reconocimientoService;

  @RequestMapping(value = "/reconocer", method = RequestMethod.POST)
  public ReconocerPersonaResult reconocer(@RequestBody byte[] payload) {
    log.info(Constantes.INFO_RECONOCIMIENTO);
    if (this.esImagenValida(payload)) {
      Persona persona = reconocimientoService.recognize(payload);
      if (persona != null) {
        return new ReconocerPersonaResult(true, persona.estaHabilitado(),
            persona.getNombreCompleto(), Constantes.LOG_BIENVENIDA);
      }
      return new ReconocerPersonaResult(false, false, null, Constantes.ERROR_PERSONA_NORECONOCIDA);
    }
    throw new RuntimeException(Constantes.ERROR_IMAGEN_INVALIDA);
  }

  public void registrarPersona(@RequestParam String personaId, @RequestBody byte[] payload) {
    // TODO : buscar la persona, y persistir la foto en un archivo de imagen

    // Leer imagen para verificar
    // indicar el formato de la imagen
    // Guadar el archivo
    // Asociar el archivo a las fotos de la persona
  }

  private boolean esImagenValida(byte[] payload) {
    try {
      ImageIO.read(new ByteArrayInputStream(payload));
      return true;
    } catch (IOException e) {
      log.debug("method esImagenValida :: ", e);
      return false;
    }
  }

}
