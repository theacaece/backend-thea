package edu.caece.app.controller;

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
		String reconocido = reconocimientoService.reconocer(payload);
		return new DetectionResult(true, reconocido, null);
	}

}