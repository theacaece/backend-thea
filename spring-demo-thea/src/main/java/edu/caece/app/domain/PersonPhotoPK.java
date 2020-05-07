package edu.caece.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PersonPhotoPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "photo_id")
	private String photoId;
	
	@Column(name = "person_id")
	private long personId;
	
	public PersonPhotoPK() {
	
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public long getPersonId() {
		return personId;
	}

	public void setPersonId(long personId) {
		this.personId = personId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (personId ^ (personId >>> 32));
		result = prime * result + ((photoId == null) ? 0 : photoId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PersonPhotoPK other = (PersonPhotoPK) obj;
		if (personId != other.personId)
			return false;
		if (photoId == null) {
			if (other.photoId != null)
				return false;
		} else if (!photoId.equals(other.photoId))
			return false;
		return true;
	}
}
