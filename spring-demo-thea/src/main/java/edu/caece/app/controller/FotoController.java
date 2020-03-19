package edu.caece.app.controller;

import java.util.Collection;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Foto;
import edu.caece.app.repository.IFotoRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http:localhost:4200")
@Slf4j
public class FotoController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private IFotoRepositorio fotoRepositorio;

  @RequestMapping(value = "/fotos", method = RequestMethod.GET)
  public Collection<Foto> getAll() {
    return fotoRepositorio.findAll();
  }

  @RequestMapping(value = "/fotos/dni/{dni}", method = RequestMethod.GET)
  public ResponseEntity<Object> getAllByDni(@PathVariable String dni) {
    log.info("Buscando fotos con DNI: " + dni);
    List<Foto> fotos = fotoRepositorio.findFotosByDni(dni);
    if (fotos == null || fotos.size() == 0) {
      log.warn("Fotos no encontradas");
      return new ResponseEntity<>(Constantes.ERROR_FOTO_INEXISTENTE, HttpStatus.NOT_FOUND);
    } else {
      log.info("Fotos encontradas, cantidad: " + fotos.size());
      return new ResponseEntity<>(fotoRepositorio.findFotosByDni(dni), HttpStatus.OK);
    }
  }

}
