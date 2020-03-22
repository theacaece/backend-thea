package edu.caece.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.caece.app.domain.Registro;
import edu.caece.app.domain.Usuario;

public interface RegistroRepositorio extends JpaRepository<Registro, Long> {

  @Modifying
  @Query("UPDATE Registro r SET r.nombre = :nombre, r.apellido = :apellido, r.dni = :dni, r.fechaIngreso = :fechaIngreso where r.id = :id")
  void updateRegistro(@Param("nombre") String nombre, @Param("apellido") String apellido,
      @Param("dni") String dni, @Param("fechaIngreso") String fechaIngreso, @Param("id") Long id);

  Optional<Registro> getRegistroById(Long id);

  Usuario findByDni(String dni);

  boolean existsByDni(String dni);

}
