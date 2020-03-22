package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import edu.caece.app.domain.Usuario;

public interface UsuarioRepositorio extends JpaRepository<Usuario, Long> {

  Usuario findByUsername(String usuario);

  boolean existsByUsername(String usuario);

}
