package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import edu.caece.app.domain.SeguridadLog;

public interface SeguridadLogRepositorio extends JpaRepository<SeguridadLog, Long> {
  @Query("from SeguridadLog where id > ?1 AND id < ?1 + ?2")
  public List<SeguridadLog> buscarLogsDe(Long id, Integer limit);

}
