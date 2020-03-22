package edu.caece.app.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import edu.caece.app.domain.SeguridadLog;
import edu.caece.app.repository.SeguridadLogRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class SeguridadLogController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SeguridadLogRepositorio seguridadLogRepositorio;

  @RequestMapping(path = "/SeguridadLogs", method = RequestMethod.GET)
  public List<SeguridadLog> getSecuridadLogs(@RequestParam(required = false) Long afterId,
      @RequestParam(required = false) Integer limit) {
    return seguridadLogRepositorio.buscarLogsDe(afterId != null ? afterId : -1,
        limit != null ? limit : 20);
  }

}
