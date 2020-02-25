package edu.caece.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.UserPhoto;

public interface IUserPhotoRepository extends JpaRepository<UserPhoto, String> {

	List<UserPhoto> findByUserId(long userId);
	
}
