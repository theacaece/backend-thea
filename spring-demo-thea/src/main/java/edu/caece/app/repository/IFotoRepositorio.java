package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import edu.caece.app.domain.Foto;

@RepositoryRestResource(collectionResourceRel = "fotos", path = "fotos")
@CrossOrigin(origins = "http://localhost:4200")

public interface IFotoRepositorio extends JpaRepository<Foto, Long> {

	@Query("SELECT f FROM Foto f WHERE f.persona.dni = :dni")
	List<Foto> findFotosByDni(@Param("dni") String dni);

}
