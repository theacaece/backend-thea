package edu.caece.app.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Photo;
import edu.caece.app.repository.IPhotoRepositorio;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoController {
	
	@Autowired
	private IPhotoRepositorio fotoRepositorio;
	
	@GetMapping("/fotos")
	public Collection<Photo> fotos() throws Exception {
		Collection<Photo> fotos = null;
		try {
			fotos = fotoRepositorio.findAll().stream().collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception("method fotossss :: " + e.getMessage());
		}
		return fotos;
	}
}
