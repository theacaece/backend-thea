package edu.caece.app.repository;

import java.util.List;

import edu.caece.app.domain.Photo;

public interface IPhotoDao {

	List<Photo> getPhotos();
	
}