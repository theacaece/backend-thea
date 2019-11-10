package edu.caece.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

import edu.caece.app.config.Hash;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IUserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private IUserRepository repository;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Collection<User> get() {

		List<User> users = new ArrayList<User>();

		repository.findAll().forEach(x -> {
			x.setPassword(null);
			users.add(x);
		});
		return users.stream().collect(Collectors.toList());
	}

	@PostMapping("/users/create")
	public ResponseEntity<String> save(@RequestBody User user) {

		boolean existe = repository.existsByUsername(user.getUsername());

		if (!existe) {
			user.setPassword(Hash.sha1(user.getPassword()));
			repository.save(user);
			return new ResponseEntity<String>("el usuario ha sido creado!", HttpStatus.OK);
		}

		return new ResponseEntity<String>("ERROR: el usuario " + "\"" + user.getUsername() + "\"" + " ya existe",
				HttpStatus.NOT_FOUND);
	}

	@PostMapping("/users/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user) {

		Optional<User> _userData = repository.findById(id);
		boolean existe_username = repository.existsByUsername(user.getUsername());

		if (_userData.isPresent()) {
			User _user = _userData.get();
			if (!existe_username || _user.getUsername().equals(user.getUsername())) {
				_user.setFirstName(user.getFirstName());
				_user.setLastName(user.getLastName());
				_user.setEmail(user.getEmail());
				_user.setUsername(user.getUsername());
				_user.setRoles(user.getRoles());

				return new ResponseEntity<>(repository.save(_user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("ERROR: el usuario " + "\"" + user.getUsername() + "\"" + " ya existe",
						HttpStatus.NOT_FOUND);
			}

		} else {
			return new ResponseEntity<>("Error: el usuario no fue encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/users/delete/{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) {

		Optional<User> _userData = repository.findById(id);

		if (_userData.isPresent()) {
			repository.deleteById(id);
			return new ResponseEntity<String>("el usuario ha sido eliminado!", HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Usuario no encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/users/edit/{id}")
	public ResponseEntity<User> getById(@PathVariable Long id) {

		Optional<User> _userData = repository.findById(id);

		if (_userData.isPresent()) {
			return new ResponseEntity<User>(repository.findById(id).get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("users/exists/{username}")
	public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
		return new ResponseEntity<>(repository.existsByUsername(username), HttpStatus.OK);
	}
}