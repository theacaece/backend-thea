package edu.caece.app.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Usuario;

public interface IPersonaRepositorio extends JpaRepository<Persona, Long> {

  @Modifying
  @Query("UPDATE Persona p SET p.nombre = :nombre, p.apellido = :apellido, p.dni = :dni, p.matricula = :matricula where p.id = :id")
  void updatePersona(@Param("nombre") String nombre, @Param("apellido") String apellido,
      @Param("dni") String dni, @Param("matricula") String matricula, @Param("id") Long id);

  Optional<Persona> getPersonaById(Long id);

  Usuario findByDni(String dni);

  boolean existsByDni(String dni);

  boolean existsByMatricula(String dni);

}
