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

import edu.caece.app.domain.User;
import edu.caece.app.repository.IUserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

	@Autowired
	private IUserRepository userRepositorio;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Collection<User> getUsuarios() {
		return userRepositorio.findAll();
	}
	
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
	public Optional<User> getUsuarioById(@PathVariable Long id) {
		return userRepositorio.findById(id);
	}
	
	@PostMapping("/users/save")
	public void saveUsuario(@RequestBody User user) {
		userRepositorio.save(user);
	}
	
	@DeleteMapping(path = { "/users/{id}" })
	public void deleteUsuario(@PathVariable("id") Long id) {
		userRepositorio.deleteById(id);
	}

	@PostMapping("/users/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody User user) {

		Optional<User> _userData = userRepositorio.findById(id);
		boolean existe_username = userRepositorio.existsByUsername(user.getUsername());

		if (_userData.isPresent()) {
			User _user = _userData.get();
			if (!existe_username || _user.getUsername().equals(user.getUsername())) {
				_user.setFirstname(user.getFirstname());
				_user.setLastname(user.getLastname());
				_user.setEmail(user.getEmail());
				_user.setUsername(user.getUsername());
				_user.setRoles(user.getRoles());

				return new ResponseEntity<>(userRepositorio.save(_user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>("ERROR: el usuario " + "\"" + user.getUsername() + "\"" + " ya existe",
						HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>("Error: el usuario no fue encontrado!", HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("users/exists/{username}")
	public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
		return new ResponseEntity<>(userRepositorio.existsByUsername(username), HttpStatus.OK);
	}
}