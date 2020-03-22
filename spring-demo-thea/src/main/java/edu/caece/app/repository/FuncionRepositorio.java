package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.Funcion;

public interface FuncionRepositorio extends JpaRepository<Funcion, Long> {

}
