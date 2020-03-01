package edu.caece.app.controller;

import java.util.Collection;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
import edu.caece.app.domain.Persona;
import edu.caece.app.repository.IPersonaRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class PersonaController {

  @Autowired
  private IPersonaRepositorio personaRepositorio;

  private Sort sortByApellido() {
    return new Sort(Sort.Direction.ASC, "apellido");
  }

  @RequestMapping(value = "/personas", method = RequestMethod.GET)
  public Collection<Persona> getAll() {
    return personaRepositorio.findAll(sortByApellido());
  }

  @RequestMapping(value = "/personas/edit/{id}", method = RequestMethod.GET)
  public Optional<Persona> getById(@PathVariable Long id) {
    return personaRepositorio.findById(id);
  }

  @PostMapping("/personas/save")
  public ResponseEntity<Object> save(@RequestBody String dni) {
    boolean existe_dni = personaRepositorio.existsByDni(dni);
    if (!existe_dni) {
      return new ResponseEntity<>(Constantes.ERROR_MATRICULA_EXISTENTE, HttpStatus.NOT_FOUND);
    } else {
      return new ResponseEntity<>(Constantes.INFO_DNI_EXISTENTE, HttpStatus.NOT_FOUND);
    }
  }

  /**
   * @param id
   * @param persona
   * @return
   */
  @PostMapping("/personas/update/{id}")
  public ResponseEntity<Object> update(@PathVariable Long id, @RequestBody Persona persona) {

    Optional<Persona> _persona = personaRepositorio.findById(id);
    boolean existe_dni = personaRepositorio.existsByDni(persona.getDni());
    boolean existe_matricula = personaRepositorio.existsByDni(persona.getMatricula());

    if (_persona.isPresent()) {
      Persona _person = _persona.get();
      if (!existe_dni || _person.getDni().equals(persona.getDni())) {
        if (!existe_matricula || _person.getMatricula().equals(persona.getMatricula())) {
          _person.setNombre(persona.getNombre());
          _person.setApellido(persona.getApellido());
          _person.setDni(persona.getDni());
          _person.setMatricula(persona.getMatricula());
          return new ResponseEntity<>(personaRepositorio.save(_person), HttpStatus.OK);
        } else {
          return new ResponseEntity<>(Constantes.ERROR_PERSONA_INEXISTENTE, HttpStatus.NOT_FOUND);
        }
      } else {
        return new ResponseEntity<>(Constantes.ERROR_PERSONA_INEXISTENTE, HttpStatus.NOT_FOUND);
      }
    } else {
      return new ResponseEntity<>(Constantes.ERROR_PERSONA_INEXISTENTE, HttpStatus.NOT_FOUND);
    }
  }

  @DeleteMapping(path = {"/personas/{id}"})
  public void delete(@PathVariable("id") Long id) {
    personaRepositorio.deleteById(id);
  }

  @GetMapping("personas/exists/{dni}")
  public ResponseEntity<Boolean> existByDni(@PathVariable String dni) {
    return new ResponseEntity<>(personaRepositorio.existsByDni(dni), HttpStatus.OK);
  }

  @GetMapping("personas/exists/{matricula}")
  public ResponseEntity<Boolean> existByMatricula(@PathVariable String matricula) {
    return new ResponseEntity<>(personaRepositorio.existsByMatricula(matricula), HttpStatus.OK);
  }

}
