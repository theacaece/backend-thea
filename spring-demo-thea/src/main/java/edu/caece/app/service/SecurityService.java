package edu.caece.app.service;

import edu.caece.app.domain.EventType;
import edu.caece.app.domain.Persona;

public interface SecurityService {
  public void logAccess(Persona person);

  public void logAccessThresholdNotMet(Persona person);

  public void log(String message, EventType eventType);

  public void log(String module, String message, EventType eventType);

  void log(String module, String message, Persona person, EventType eventType);
}
