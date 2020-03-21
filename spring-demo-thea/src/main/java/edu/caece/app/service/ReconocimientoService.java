package edu.caece.app.service;

import org.springframework.stereotype.Service;
import edu.caece.app.domain.Persona;

@Service
public interface ReconocimientoService {

  public Persona reconocerIngreso(byte[] imagenCara);

}
