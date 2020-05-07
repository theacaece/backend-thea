package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.UserLog;

public interface IUserLogRepository extends JpaRepository<UserLog, String> {
	
	List<UserLog> findByUserId(long userId);
	
}
