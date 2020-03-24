package edu.caece.app.service.impl;

import static java.lang.String.format;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.SeguridadLog;
import edu.caece.app.domain.TipoEvento;
import edu.caece.app.repository.SeguridadLogRepositorio;
import edu.caece.app.service.SeguridadService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SeguridadServiceImpl implements SeguridadService {

  private static final String DEFAULT_MODULE = "BACKEND";

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private SeguridadLogRepositorio seguridadLogRepositorio;

  @Override
  public void verificarIngreso(Persona persona) {
    String mensaje = format(Constantes.LOG_ACCESO_PEDIDO,
        persona.isHabilitado() ? Constantes.LOG_ACCESO_AUTORIZADO : Constantes.LOG_ACCESO_DENEGADO,
        persona.getNombreCompleto(), persona.getDni());
    this.log(DEFAULT_MODULE, mensaje, persona,
        persona.isHabilitado() ? TipoEvento.ACCESO_OK : TipoEvento.ACCESO_DENEGADO);
    log.info(mensaje);
  }

  @Override
  public void verificarIngresoUmbralNoAlcanzado(Persona persona) {
    String mensaje =
        format(Constantes.LOG_ACCESO_NOCONFIABLE, persona.getNombreCompleto(), persona.getDni());
    this.log(DEFAULT_MODULE, mensaje, persona, TipoEvento.ACCESO_UMBRAL_NO_VALIDO);
    log.info(mensaje);
  }

  @Override
  public void log(String mensaje, TipoEvento tipoEvento) {
    log(null, mensaje, tipoEvento);
  }

  @Override
  public void log(String modulo, String mensaje, TipoEvento tipoEvento) {
    this.log(modulo, mensaje, null, tipoEvento);
  }

  @Override
  @Transactional
  public void log(String modulo, String mensaje, Persona persona, TipoEvento tipoEvento) {
    log.info(format(Constantes.LOG_ACCESO_NOCONFIABLE, modulo, tipoEvento, persona, mensaje));
    SeguridadLog seguridadLog = new SeguridadLog(modulo, mensaje, persona, tipoEvento);
    seguridadLogRepositorio.save(seguridadLog);
  }

}
