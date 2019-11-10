package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import edu.caece.app.domain.Photo;

@RepositoryRestResource(collectionResourceRel = "photos", path = "photos")
@CrossOrigin(origins = "http://localhost:4200")

public interface IPhotoRepository extends JpaRepository<Photo, Long> {

}
