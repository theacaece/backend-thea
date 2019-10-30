package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.Role;

public interface IRoleRepository extends JpaRepository<Role, Long> {

}
