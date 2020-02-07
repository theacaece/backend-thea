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

import edu.caece.app.controller.dto.ReconocerPersonaResult;
import edu.caece.app.domain.Person;
import edu.caece.app.service.ReconocimientoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReconocedorController {
	
	private final static Logger logger = LoggerFactory.getLogger(ReconocedorController.class);

	@Autowired
	private ReconocimientoService reconocimientoService;

	@RequestMapping(value = "/reconocer", method = RequestMethod.GET)
	public ReconocerPersonaResult reconocer(@RequestBody byte[] payload) {
		if (this.isValidImage(payload)) {
			Person persona = reconocimientoService.recognize(payload);
			if (persona!= null) {
				return new ReconocerPersonaResult(true, persona.isEntryAllowed(), persona.getFullName(), "Bienvenido a la facultad");
			}
			return new ReconocerPersonaResult(false, false, null, "La persona no fue reconocida");
		}
		// TODO: handle response code and message
		throw new RuntimeException("La imagen provista en la invocación es invalida");
	}
	
	public void registrarPersona(@RequestParam String personaId, @RequestBody byte[] payload) {
		// TODO : buscar la persona, y persistir la foto en un archivo de imagen
		
		// Leer imagen para verificar
		// indicar el formato de la imagen
		// Guadar el archivo
		// Asociar el archivo a las fotos de la persona
	}

	private boolean isValidImage(byte[] payload){
		try {
			ImageIO.read(new ByteArrayInputStream(payload));
			return true;
		} catch (IOException e) {
			// Logging: Error al leer la imagen, retornar un error
			logger.debug("An invalid Image was provided", e);
			return false;
		}
	}

}