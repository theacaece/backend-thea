package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.PersonPhoto;

public interface IPersonPhotoRepository extends JpaRepository<PersonPhoto, String> {
	
	List<PersonPhoto> findByPersonId(long personId);
	
}
