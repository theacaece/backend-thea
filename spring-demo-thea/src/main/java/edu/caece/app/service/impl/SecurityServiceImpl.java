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
import edu.caece.app.domain.EventType;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.SecurityLog;
import edu.caece.app.repository.SecurityLogRepository;
import edu.caece.app.service.SecurityService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityServiceImpl implements SecurityService {

  private static final Logger logger = LoggerFactory.getLogger(SecurityServiceImpl.class);


  private static final String DEFAULT_MODULE = "BACKEND";
  @Autowired
  private SecurityLogRepository securityLogRepository;

  @Override
  public void logAccess(Persona person) {
    String message = format("Acceso %s para %s (%s)",
        person.isEntryAllowed() ? "autorizado" : "denegado", person.getFullName(), person.getDni());
    this.log(null, message, person, person.isEntryAllowed() ? GRANTED_ACCESS : DENIED_ACCESS);
    logger.info(message);
  }

  @Override
  public void logAccessThresholdNotMet(Persona person) {
    String message =
        format("No se pudo autorizar a la persona, confianza menor al 60% para %s (%s)",
            person.getFullName(), person.getDni());
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
    SecurityLog securityLog = new SecurityLog(module, message, person, eventType);
    securityLogRepository.save(securityLog);
    // TODO: FIXME: Avoid null values being logged
    logger.info(format("%s - %s (Person:%s): %s", module, eventType, person, message));
  }

}
