package edu.caece.app.service.impl;

import static edu.caece.app.domain.EventType.DENIED_ACCESS;
import static edu.caece.app.domain.EventType.GRANTED_ACCESS;
import static edu.caece.app.domain.EventType.RECOGNIZED_THRESHOLD_NOT_MET;
import static java.lang.String.format;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.caece.app.Constantes;
import edu.caece.app.domain.EventType;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.SeguridadLog;
import edu.caece.app.repository.SeguridadLogRepositorio;
import edu.caece.app.service.SecuridadService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeguridadServiceImpl implements SecuridadService {

  private static final String DEFAULT_MODULE = "BACKEND";

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SeguridadLogRepositorio seguridadLogRepositorio;

  @Override
  public void logAccess(Persona person) {
    String message = format(Constantes.LOG_ACCESO_PEDIDO,
        person.isHabilitado() ? Constantes.LOG_ACCESO_AUTORIZADO : Constantes.LOG_ACCESO_DENEGADO,
        person.getNombreCompleto(), person.getDni());
    this.log(null, message, person, person.isHabilitado() ? GRANTED_ACCESS : DENIED_ACCESS);
    log.info(message);
  }

  @Override
  public void logAccessThresholdNotMet(Persona person) {
    String message =
        format(Constantes.LOG_ACCESO_NOCONFIABLE, person.getNombreCompleto(), person.getDni());
    this.log(DEFAULT_MODULE, message, person, RECOGNIZED_THRESHOLD_NOT_MET);
  }

  @Override
  public void log(String message, EventType eventType) {
    log(null, message, eventType);
  }

  @Override
  public void log(String module, String message, EventType eventType) {
    this.log(module, message, null, eventType);
  }

  @Override
  @Transactional
  public void log(String module, String message, Persona person, EventType eventType) {
    SeguridadLog securityLog = new SeguridadLog(module, message, person, eventType);
    seguridadLogRepositorio.save(securityLog);
    log.info(format(Constantes.LOG_ACCESO_NOCONFIABLE, module, eventType, person, message));
  }

}
