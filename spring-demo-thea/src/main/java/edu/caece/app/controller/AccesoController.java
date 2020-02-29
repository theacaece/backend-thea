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
import edu.caece.app.domain.Acceso;
import edu.caece.app.repository.IAccesoRepositorio;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AccesoController {

  @Autowired
  private IAccesoRepositorio accesoRepositorio;

  @RequestMapping(value = "/accesos", method = RequestMethod.GET)
  public Collection<Acceso> getAll() {
    return accesoRepositorio.findAll();
  }

  @RequestMapping(value = "/accesos/edit/{id}", method = RequestMethod.GET)
  public Optional<Acceso> getById(@PathVariable Long id) {
    return accesoRepositorio.findById(id);
  }

  @PostMapping("/accesos/save")
  public void save(@RequestBody Acceso acceso) {
    accesoRepositorio.save(acceso);
  }

}
