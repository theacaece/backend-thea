package edu.caece.app.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users_photos")
public class UserPhoto {

	@Id
	@Column(name = "user_id")
	private long userId;

	@OneToOne
	@MapsId("userId")
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;

	@Lob
	@Column(name = "photo")
	@Basic(fetch = FetchType.LAZY)
	private byte[] photo;

	public UserPhoto() {

	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public byte[] getPhoto() {
		return photo;// Base64.getUrlEncoder().encodeToString(photo);
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo; //Base64.getUrlDecoder().decode("url('" + photo + "')");
	}
}
