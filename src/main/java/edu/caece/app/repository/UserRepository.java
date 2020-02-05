package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	boolean existsByUsername(String username);
		
}