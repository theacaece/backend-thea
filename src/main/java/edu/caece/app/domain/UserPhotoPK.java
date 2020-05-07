package edu.caece.app.domain;

import java.io.Serializable;

import javax.persistence.Column;


public class UserPhotoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "photo_id")
	private String photoId;

	@Column(name = "user_id")
	private long userId;

}
