package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.User;

public interface IUserRepository extends JpaRepository<User, String>{

}
