package edu.caece.app.service;

import org.springframework.stereotype.Service;

import edu.caece.app.domain.Person;

@Service
public interface ReconocimientoService {

	public Person recognize(byte[] imagenCara);

}
