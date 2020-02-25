package edu.caece.app.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.caece.app.config.Hash;

@Entity
@Table(name = "users_photos")
public class UserPhoto {

	@EmbeddedId
	private UserPhotoPK id;

	@ManyToOne
	@MapsId("user_id")
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@Column(name = "photo")
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Lob()
	private byte[] photo;

	public UserPhoto() {
		this.id = new UserPhotoPK();
		id.setPhotoId(Hash.getId());
	}

	public UserPhotoPK getId() {
		return id;
	}

	public void setId(UserPhotoPK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.id.setUserId(user.getId());
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}
