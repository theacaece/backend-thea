package edu.caece.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Person;
import edu.caece.app.repository.IPersonRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {
	
	@Autowired
	private IPersonRepository repository;
	
	@RequestMapping(value = "/persons", method = RequestMethod.GET)
	public Collection<Person> get() {
		List<Person> persons = new ArrayList<Person>();
		repository.findAll().forEach(x -> {
			persons.add(x);
		});
		return persons.stream().collect(Collectors.toList());
	}
	
	public Collection<Person> personas() throws Exception {
		Collection<Person> personas = null;
		try {
//			personas = repository.findAll().stream().collect(Collectors.toList());
		} catch (Exception e) {
			throw new Exception("method persons :: " + e.getMessage());
		}
		return personas;
	}
	
//	@GetMapping("/personas/{id}")
//	public Person persona(String id) throws Exception {
////		return repository.getOne(id);
//	}
	
	@PostMapping("/personas/save")
	public void save(@RequestBody Person persona) throws Exception {
		try {
//			this.repository.save(persona);
		} catch (Exception e) {
			throw new Exception("method persona :: save :: " + e.getMessage());
		}
	}
	
	@DeleteMapping("/personas/delete/{id}")
	public void deleteById(String id) throws Exception {
		try {
//			this.repository.deleteById(id);
		} catch (Exception e) {
			throw new Exception("method persona :: deleteById :: " + e.getMessage());
		}
	}

}
