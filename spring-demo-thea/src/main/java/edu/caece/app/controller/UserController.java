	package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

import edu.caece.app.Constantes;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.IUsuarioRepositorio;
import org.springframework.data.domain.Sort;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IUsuarioRepositorio userRepositorio;

	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public Collection<Usuario> getAll() {
		return userRepositorio.findAll(sortByApellido());
	}

    private Sort sortByApellido() {
        return new Sort(Sort.Direction.ASC, "lastname");
    }
	
	@RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
	public Optional<Usuario> getById(@PathVariable Long id) {
		return userRepositorio.findById(id);
	}
	
	@PostMapping("/users/save")
	public ResponseEntity<Object> save(@RequestBody Usuario user) {
		log.info(user.toString());
		log.info(Constantes.INFO_USUARIO_SAVE);
		boolean existe = userRepositorio.existsByUsername(user.getUsername());
		if (!existe) {
			return new ResponseEntity<>(userRepositorio.save(user), HttpStatus.OK);
		} else {
			log.error(Constantes.ERROR_USUARIO_EXISTENTE);
			return new ResponseEntity<>(Constantes.ERROR_USUARIO_EXISTENTE, HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = { "/users/{id}" })
	public void delete(@PathVariable("id") Long id) {
		log.info(Constantes.INFO_USUARIO_DELETE);
		userRepositorio.deleteById(id);
	}

	@PostMapping("/users/update/{id}")
	public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Usuario user) {
		log.info(Constantes.INFO_USUARIO_UPDATE);

		Optional<Usuario> _userData = userRepositorio.findById(id);
		boolean existe_username = userRepositorio.existsByUsername(user.getUsername());

		if (_userData.isPresent()) {
			Usuario _user = _userData.get();
			if (!existe_username || _user.getUsername().equals(user.getUsername())) {
				_user.setFirstname(user.getFirstname());
				_user.setLastname(user.getLastname());
				_user.setEmail(user.getEmail());
				_user.setUsername(user.getUsername());
				_user.setRoles(user.getRoles());

				return new ResponseEntity<>(userRepositorio.save(_user), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(Constantes.ERROR_USUARIO_INEXISTENTE, HttpStatus.NOT_FOUND);
			}
		} else {
			return new ResponseEntity<>(Constantes.ERROR_USUARIO_INEXISTENTE, HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("users/exists/{username}")
	public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
		return new ResponseEntity<>(userRepositorio.existsByUsername(username), HttpStatus.OK);
	}
}