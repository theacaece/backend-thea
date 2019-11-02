package edu.caece.app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.domain.User;
import edu.caece.app.repository.IUserRepository;

@RestController
@CrossOrigin
public class UserController {

	@Autowired
	private IUserRepository repository;

	@GetMapping("/users/list")
	public List<User> get() {

		return repository.findAll();
	}
	
	@PostMapping("/users/save")
	public void save(@RequestBody User user) {
		repository.save(user);
	}
	
	@DeleteMapping("/users/delete/{id}")
	public void delete(@RequestParam Long id) {
		repository.deleteById(id);
	}
	
	@GetMapping("/users/edit/{id}")
	public Optional<User> getById(@RequestParam Long id) {
		return repository.findById(id);
	}
	
	
}
