package edu.caece.app.service.impl;

import static java.lang.String.format;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import edu.caece.app.Constantes;
import edu.caece.app.domain.Ingreso;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.TipoEvento;
import edu.caece.app.repository.IngresoRepositorio;
import edu.caece.app.service.IngresoService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class IngresoImpl implements IngresoService {

	protected final Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private IngresoRepositorio registroRepositorio;

	@Override
	public void verificarIngreso(Persona persona) {
		String mensaje = format(Constantes.LOG_ACCESO_PEDIDO,
				persona.isHabilitado() ? Constantes.LOG_ACCESO_AUTORIZADO : Constantes.LOG_ACCESO_DENEGADO,
				persona.getNombreCompleto(), persona.getDni());
		this.log(persona,
				persona.isHabilitado() ? TipoEvento.ACCESO_OK : TipoEvento.ACCESO_DENEGADO, mensaje);
		log.info(mensaje);
	}

	@Override
	public void verificarIngresoUmbralNoAlcanzado(Persona persona) {
		String mensaje = format(Constantes.LOG_ACCESO_NOCONFIABLE, persona.getNombreCompleto(), persona.getDni());
		this.log(persona, TipoEvento.ACCESO_UMBRAL_NO_VALIDO, mensaje);
		log.info(mensaje);
	}

	@Override
	@Transactional
	public void log(Persona persona, TipoEvento tipoEvento, String mensaje) {
		Ingreso registro = new Ingreso(persona, tipoEvento, mensaje);
		registroRepositorio.save(registro);
	}

}
