package edu.caece.app.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(String name);
	
}