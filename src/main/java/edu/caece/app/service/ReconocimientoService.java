package edu.caece.app.service;

import org.springframework.stereotype.Service;

@Service
public interface ReconocimientoService {

	public String reconocer(byte[] imagenCara);

}
