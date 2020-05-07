package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.UserPhoto;

public interface IUserPhotoRepository extends JpaRepository<UserPhoto, Long> {

	UserPhoto findByUserId(long id);
}
