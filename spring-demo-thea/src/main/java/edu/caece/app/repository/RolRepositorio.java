package edu.caece.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.Rol;

public interface RolRepositorio extends JpaRepository<Rol, Long> {

  Optional<Rol> findByNombre(String nombre);

}
