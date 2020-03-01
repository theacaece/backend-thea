package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Registro;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.repository.IRegistroRepositorio;
import edu.caece.app.resources.FuncionesUtiles;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class RegistroController {

  final Logger logger = LoggerFactory.getLogger(RegistroController.class);

  @Autowired
  private IPersonaRepositorio personaRepositorio;

  @Autowired
  private IRegistroRepositorio registroRepositorio;

  private Sort sortByFechaIngresoDesc() {
    return new Sort(Sort.Direction.DESC, "fechaIngreso");
  }

  @RequestMapping(value = "/registros", method = RequestMethod.GET)
  public Collection<Registro> getAll() {
    logger.info(Constantes.INFO_REGISTRO_ALL);
    return registroRepositorio.findAll(sortByFechaIngresoDesc());
  }

  @PostMapping("/registros/save")
  public ResponseEntity<Object> save(@RequestBody String dni) {
    logger.info(Constantes.INFO_REGISTRO_SAVE);
    boolean existe_dni = personaRepositorio.existsByDni(dni);
    if (existe_dni) {
      logger.info(Constantes.ERROR_DNI_INEXISTENTE);
      Optional<Persona> _persona = personaRepositorio.findByDni(dni);
      if (_persona.isPresent()) {
        logger.info(Constantes.INFO_PERSONA_EXISTENTE);
        Persona _person = _persona.get();
        Registro registro = new Registro();
        registro.setNombre(_person.getNombre());
        registro.setApellido(_person.getApellido());
        registro.setDni(_person.getDni());
        registro.setFechaIngreso(FuncionesUtiles.obtenerFechaHoy());
        logger.info(registro.toString());
        return new ResponseEntity<>(registroRepositorio.save(registro), HttpStatus.OK);
      }
    }
    logger.info(Constantes.ERROR_DNI_INEXISTENTE);
    return new ResponseEntity<>(Constantes.ERROR_PERSONA_INEXISTENTE, HttpStatus.NOT_FOUND);
  }

}
