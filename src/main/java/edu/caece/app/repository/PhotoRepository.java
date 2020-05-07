package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Photo;

public interface PhotoRepository extends JpaRepository<Photo, String> {
	
	List<Photo> findByPersonId(long personId);
	
}
