package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.PersonLog;

public interface IPersonLogRepository extends JpaRepository<PersonLog, String> {

	List<PersonLog> findByPersonId(long perosonId);
	
}