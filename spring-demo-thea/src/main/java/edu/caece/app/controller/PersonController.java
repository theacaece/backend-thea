package edu.caece.app.controller;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Person;
import edu.caece.app.repository.IPersonRepositorio;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {
	
	@Autowired
	private IPersonRepositorio personaRepositorio;
	
	@GetMapping("/personas")
	public Collection<Person> personas() throws Exception {
		Collection<Person> personas = null;
		try {
			personas = personaRepositorio.findAll().stream().collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception("method personas :: " + e.getMessage());
		}
		return personas;
	}
	
	@GetMapping("/personas/{id}")
	public Person persona(String id) throws Exception {
		return personaRepositorio.getOne(id);
	}
	
	@PostMapping("/personas/save")
	public void save(@RequestBody Person persona) throws Exception {
		try {
			this.personaRepositorio.save(persona);
		} catch (Exception e) {
			throw new Exception("method persona :: save :: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/personas/delete/{id}")
	public void deleteById(String id) throws Exception {
		try {
			this.personaRepositorio.deleteById(id);
		} catch (Exception e) {
			throw new Exception("method persona :: deleteById :: " + e.getMessage());
		}
	}

}
