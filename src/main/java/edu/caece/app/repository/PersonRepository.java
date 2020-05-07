package edu.caece.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.caece.app.domain.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
	
	public Optional<Person> findByMatricula(String matricula);
	
	public Optional<Person> findByDni(String dni);
	
}
