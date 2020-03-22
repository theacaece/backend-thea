package edu.caece.app.service;

import edu.caece.app.domain.Persona;
import edu.caece.app.domain.TipoEvento;

public interface SeguridadService {

  public void verificarIngreso(Persona person);

  public void verificarIngresoThresholdNotMet(Persona persona);

  public void log(String message, TipoEvento tipoEvento);

  public void log(String module, String message, TipoEvento tipoEvento);

  void log(String module, String message, Persona person, TipoEvento tipoEvento);
}
