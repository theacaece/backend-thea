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
import edu.caece.app.repository.UsuarioRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class UsuarioController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private UsuarioRepositorio repositorio;

  @RequestMapping(value = "/users", method = RequestMethod.GET)
  public Collection<Usuario> getUsuarios() {
    log.info(Constantes.INFO_USUARIO_ALL);
    return repositorio.findAll();
  }

  @RequestMapping(value = "/users/edit/{id}", method = RequestMethod.GET)
  public Optional<Usuario> getUsuarioById(@PathVariable Long id) {
    log.info(Constantes.INFO_USUARIO_ONE);
    return repositorio.findById(id);
  }

  @PostMapping("/users/save")
  public void saveUsuario(@RequestBody Usuario user) {
    log.info(Constantes.INFO_USUARIO_SAVE);
    repositorio.save(user);
  }

  @DeleteMapping(path = {"/users/{id}"})
  public void deleteUsuario(@PathVariable("id") Long id) {
    log.info(Constantes.INFO_USUARIO_DELETE);
    repositorio.deleteById(id);
  }

  @PostMapping("/users/update/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Usuario user) {
    log.info(Constantes.INFO_USUARIO_UPDATE);
    Optional<Usuario> _userData = repositorio.findById(id);
    boolean existe_username = repositorio.existsByUsername(user.getUsername());
    if (_userData.isPresent()) {
      Usuario _user = _userData.get();
      if (!existe_username || _user.getUsername().equals(user.getUsername())) {
        _user.setFirstname(user.getFirstname());
        _user.setLastname(user.getLastname());
        _user.setEmail(user.getEmail());
        _user.setUsername(user.getUsername());
        _user.setRoles(user.getRoles());
        return new ResponseEntity<>(repositorio.save(_user), HttpStatus.OK);
      } else {
        return new ResponseEntity<>(
            "ERROR: el usuario " + "\"" + user.getUsername() + "\"" + " ya existe",
            HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>(Constantes.ERROR_USUARIO_INEXISTENTE, HttpStatus.NOT_FOUND);
    }
  }

  @GetMapping("users/exists/{username}")
  public ResponseEntity<Boolean> existByUsername(@PathVariable String username) {
    return new ResponseEntity<>(repositorio.existsByUsername(username), HttpStatus.OK);
  }
}
