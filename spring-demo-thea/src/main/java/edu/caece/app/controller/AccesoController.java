package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Acceso;
import edu.caece.app.repository.AccesoRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class AccesoController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private AccesoRepositorio accesoRepositorio;

  @RequestMapping(value = "/accesos", method = RequestMethod.GET)
  public Collection<Acceso> getAll() {
    log.info(Constantes.INFO_ACCESO_ALL);
    return accesoRepositorio.findAll();
  }

  @RequestMapping(value = "/accesos/edit/{id}", method = RequestMethod.GET)
  public Optional<Acceso> getById(@PathVariable Long id) {
    log.info(Constantes.INFO_ACCESO_ONE);
    return accesoRepositorio.findById(id);
  }

  @PostMapping("/accesos/save")
  public void save(@RequestBody Acceso acceso) {
    log.info(Constantes.INFO_ACCESO_SAVE);
    accesoRepositorio.save(acceso);
  }

}
