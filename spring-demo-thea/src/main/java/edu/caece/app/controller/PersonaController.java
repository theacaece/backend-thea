package edu.caece.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IPersonaRepositorio;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class PersonaController {
	
	private static final Logger LOGGER = Logger.getLogger(PersonaController.class.getName());
	
	@Autowired
	private IPersonaRepositorio repository;
	
	@RequestMapping(value = "/personas", method = RequestMethod.GET)
	public Collection<Persona> get() {
		return repository.findAll();
	}
	
	@PostMapping("/personas/save")
	public void save(@RequestBody Persona persona) {
		boolean existe = repository.existsByDni(persona.getDni());
		if (!existe) {
			repository.save(persona);
		}
	}
	
	@DeleteMapping(path = { "/personas/{id}" })
	public void delete(@PathVariable("id") Long id) {
		repository.deleteById(id);
	}
	
//	@GetMapping("/personas/{id}")
//	public Person persona(String id) throws Exception {
////		return repository.getOne(id);
//	}
	
	@GetMapping("personas/exists/{dni}")
	public ResponseEntity<Boolean> existByDni(@PathVariable String dni) {
		return new ResponseEntity<>(repository.existsByDni(dni), HttpStatus.OK);
	}

}
