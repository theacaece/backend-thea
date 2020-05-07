package edu.caece.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.Person;
import edu.caece.app.repository.IPersonRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class PersonController {

	private IPersonRepository repository;
	
	@RequestMapping(value = "/persons/list", method = RequestMethod.GET)
	public Collection<Person> get() {

		List<Person> persons = new ArrayList<Person>();
	

		repository.findAll().forEach(x -> {
			persons.add(x);
		});
		
		return persons.stream().collect(Collectors.toList());
	}

	@PostMapping("/persons/create")
	public ResponseEntity<String> save(@RequestBody Person person) {

		boolean existe = repository.existsById(person.getId());

		if (!existe) {
			repository.save(person);
			return new ResponseEntity<String>("el usuario ha sido creado!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("ERROR: La persona con Id" + "\"" + person.getId() + "\"" + " ya existe",
				HttpStatus.NOT_FOUND);
	}

	@PutMapping("/persons/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Person person) {

		Optional<Person> _personData = repository.findById(id);
		
		if (_personData.isPresent()) {
			Person _person = _personData.get();
				_person.setFirstName(person.getFirstName());
				_person.setLastName(person.getLastName());
				_person.setRegistrationNumber(person.getRegistrationNumber());
				_person.setPhotos(_person.getPhotos());
				_person.setDni(_person.getDni());
								
				return new ResponseEntity<>(repository.save(_person), HttpStatus.OK);
			
		} else {
			return new ResponseEntity<>("Error: el usuario no fue encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/persons/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		Optional<Person> _personData = repository.findById(id);

		if (_personData.isPresent()) {
			repository.deleteById(id);
			return new ResponseEntity<String>("El usuario ha sido eliminado!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Usuario no encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/persons/edit/{id}")
	public ResponseEntity<Person> getById(@PathVariable Long id) {

		Optional<Person> _personData = repository.findById(id);

		if (_personData.isPresent()) {
			return new ResponseEntity<Person>(repository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("persons/exists/{username}")
	public ResponseEntity<Boolean> existByUsername(@PathVariable long id) {
		return new ResponseEntity<>(repository.existsById(id), HttpStatus.OK);
	}
	
	
}
