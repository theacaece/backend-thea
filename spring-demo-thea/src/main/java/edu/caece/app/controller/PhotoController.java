package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Photo;
import edu.caece.app.repository.IPhotoRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PhotoController {
	
	@Autowired
	private IPhotoRepository photoRepository;
	
	@GetMapping("/photos")
	public Collection<Photo> photos() throws Exception {
		Collection<Photo> fotos = null;
		try {
			fotos = photoRepository.findAll().stream().collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception("method photos :: " + e.getMessage());
		}
		return fotos;
	}
	
	@RequestMapping(value = "/photos/edit", method = RequestMethod.GET)
	public Optional<Photo> getById(@PathVariable Long id) {
		return photoRepository.findById(id);
	}
}
