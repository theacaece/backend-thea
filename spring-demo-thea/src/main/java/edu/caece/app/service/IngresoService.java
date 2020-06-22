package edu.caece.app.service;

import edu.caece.app.domain.Persona;
import edu.caece.app.domain.TipoEvento;

public interface IngresoService {

  public void verificarIngreso(Persona persona);

  public void verificarIngresoUmbralNoAlcanzado(Persona persona);
  
  void log(Persona persona, TipoEvento tipoEvento, String mensaje);
}
