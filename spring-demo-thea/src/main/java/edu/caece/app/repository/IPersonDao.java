package edu.caece.app.repository;

import java.util.List;

import edu.caece.app.domain.Person;

public interface IPersonDao {

	List<Person> getPersonas() throws Exception;
	
}