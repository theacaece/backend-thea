package edu.caece.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.caece.app.domain.SecurityLog;

public interface SecurityLogRepository extends JpaRepository<SecurityLog, Long> {
  @Query("from SecurityLog where id > ?1 AND id < ?1 + ?2")
  public List<SecurityLog> findLogsFrom(Long id, Integer limit);

}
