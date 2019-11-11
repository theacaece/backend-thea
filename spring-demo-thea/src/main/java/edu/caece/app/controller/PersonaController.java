package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;

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

	@Autowired
	private IPersonaRepositorio repositorio;
	
	@RequestMapping(value = "/personas", method = RequestMethod.GET)
	public Collection<Persona> getPersonas() {
		return repositorio.findAll();
	}
	
	
	@RequestMapping(value = "/personas/edit/{id}", method = RequestMethod.GET)
	public Optional<Persona> getPersonaById(@PathVariable Long id) {
		return repositorio.findById(id);
	}
	
	@PostMapping("/personas/save")
	public void savePersona(@RequestBody Persona persona) {
		boolean existe = repositorio.existsByDni(persona.getDni());
		if (!existe) {
			repositorio.save(persona);
		}
	}
	
	@DeleteMapping(path = { "/personas/{id}" })
	public void deletePersona(@PathVariable("id") Long id) {
		repositorio.deleteById(id);
	}

	@GetMapping("personas/exists/{dni}")
	public ResponseEntity<Boolean> existByDni(@PathVariable String dni) {
		return new ResponseEntity<>(repositorio.existsByDni(dni), HttpStatus.OK);
	}

}
