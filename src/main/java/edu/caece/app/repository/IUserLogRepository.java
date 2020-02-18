package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.UserLog;

public interface IUserLogRepository extends JpaRepository<UserLog, Long> {

}
