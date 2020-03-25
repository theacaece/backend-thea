package edu.caece.app.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.Constantes;
import edu.caece.app.domain.Ingreso;
import edu.caece.app.repository.IngresoRepositorio;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class IngresoController {

	protected final Logger log = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private IngresoRepositorio registroRepositorio;
	
	private Sort sortByTimestampDesc() {
		return new Sort(Sort.Direction.DESC, "timestamp");
	}
	
	@RequestMapping(value = "/registros", method = RequestMethod.GET)
	public Collection<Ingreso> getAll() {
		log.info(Constantes.INFO_REGISTRO_ALL);
		return registroRepositorio.findAll(sortByTimestampDesc());
	}

}
