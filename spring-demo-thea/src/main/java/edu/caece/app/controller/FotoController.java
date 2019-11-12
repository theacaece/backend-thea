package edu.caece.app.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Foto;
import edu.caece.app.repository.IFotoRepositorio;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class FotoController {
	
	@Autowired
	private IFotoRepositorio fotoRepositorio;
	
	@RequestMapping(value = "/fotos", method = RequestMethod.GET)
	public Collection<Foto> getAll() {
		return fotoRepositorio.findAll();
	}

	/*@RequestMapping(value = "/fotos/dni", method = RequestMethod.GET)
	public ResponseEntity<Object> getAllByDni(@PathVariable String dni) {
		return new ResponseEntity<>(fotoRepositorio.findFotosByDni(dni), HttpStatus.OK);
	}*/
	
}
