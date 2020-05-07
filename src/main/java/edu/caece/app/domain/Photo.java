package edu.caece.app.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "users_photos")
public class Photo {

	@Id
	private long id;

	@Lob
	@Column(name = "photo")
	@Basic(fetch = FetchType.LAZY)
	private byte[] photo;

	public Photo() {

	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public byte[] getPhoto() {
		return photo;// Base64.getUrlEncoder().encodeToString(photo);
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo; // Base64.getUrlDecoder().decode("url('" + photo + "')");
	}

}
