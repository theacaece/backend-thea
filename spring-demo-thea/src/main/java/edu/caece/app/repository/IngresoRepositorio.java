package edu.caece.app.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import edu.caece.app.domain.Ingreso;

public interface IngresoRepositorio extends JpaRepository<Ingreso, Long> {
	
	@Query("from Ingreso where id > ?1 AND id < ?1 + ?2")
	public List<Ingreso> buscarRegistrosDe(Long id, Integer limit);

}
