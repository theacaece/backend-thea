package edu.caece.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());
	
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

	@PostMapping("/users/save")
	public void save(@RequestBody User user) {
		boolean existe = repository.existsByUsername(user.getUsername());
		if (!existe) {
			repository.save(user);
		}
	}
	
	@RequestMapping(value = "/users/edit", method = RequestMethod.GET)
	public Optional<User> getById(@PathVariable Long id) {
		LOGGER.info(" Enter >> getById() ");
		System.out.println(" Enter >> getById() ");
		return repository.findById(id);
	}
	
	@PostMapping(value="/users/delete")
	public void delete(@PathVariable Long id) {
		Optional<User> _userData = repository.findById(id);
		if (_userData.isPresent()) {
			repository.deleteById(id);
		}
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

	@GetMapping("users/exists/{username}")
	public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
		return new ResponseEntity<>(repository.existsByUsername(username), HttpStatus.OK);
	}
}