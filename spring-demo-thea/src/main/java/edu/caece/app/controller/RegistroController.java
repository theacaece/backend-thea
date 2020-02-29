package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.domain.Registro;
import edu.caece.app.repository.IRegistroRepositorio;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class RegistroController {

  @Autowired
  private IRegistroRepositorio registroRepositorio;

  @RequestMapping(value = "/registros", method = RequestMethod.GET)
  public Collection<Registro> getAll() {
    return registroRepositorio.findAll();
  }

  @RequestMapping(value = "/registros/edit/{id}", method = RequestMethod.GET)
  public Optional<Registro> getById(@PathVariable Long id) {
    return registroRepositorio.findById(id);
  }

  @PostMapping("/registros/save")
  public void save(@RequestBody Registro registro) {
    registroRepositorio.save(registro);
  }

}
