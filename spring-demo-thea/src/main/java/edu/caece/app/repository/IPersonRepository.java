package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Person;
import edu.caece.app.domain.User;

public interface IPersonRepository extends JpaRepository<Person, Long> {
	
	User findByDni(String dni);
	
	boolean existsByDni(String dni);

}
