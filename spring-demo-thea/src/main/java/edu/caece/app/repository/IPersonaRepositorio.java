package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Persona;
import edu.caece.app.domain.User;

public interface IPersonaRepositorio extends JpaRepository<Persona, Long> {
	
	User findByDni(String dni);
	
	boolean existsByDni(String dni);

}
