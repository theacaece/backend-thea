package edu.caece.app.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.service.ReconocimientoService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class ReconocedorController {

	@Autowired
	private ReconocimientoService reconocimientoService;

	@RequestMapping(value = "/evento/faceDetected", method = RequestMethod.GET)
	public DetectionResult faceDetected(@RequestBody byte[] payload) {
		if (this.isValidImage(payload)) {
			String reconocido = reconocimientoService.reconocer(payload);
			return new DetectionResult(true, reconocido, null);
		}else {
			return new DetectionResult(false, null, null);
		}
	}

	private boolean isValidImage(byte[] payload) {
		try {
			BufferedImage image = ImageIO.read(new ByteArrayInputStream(payload));
			return true;
		} catch (IOException e) {
			// Logging: Error al leer la imagen, retornar un error
		}
		return false;
	}

}