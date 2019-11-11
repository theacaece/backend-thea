package edu.caece.app.repository;

import java.util.List;

import edu.caece.app.domain.Persona;

public interface IPersonaDao {

	List<Persona> getPersonas() throws Exception;

	Persona findById(int id) throws Exception;
	
}