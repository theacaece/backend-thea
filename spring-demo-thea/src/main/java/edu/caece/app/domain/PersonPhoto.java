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

import edu.caece.app.config.Hash;

@Entity
@Table(name = "persons_photos")
public class PersonPhoto {

	@EmbeddedId
	private PersonPhotoPK id;

	@ManyToOne
	@MapsId("person_id")
	@JoinColumn(name = "person_id")
	private Person person;

	@Column(name = "photo")
	@Basic(optional = false, fetch = FetchType.EAGER)
	@Lob()
	private byte[] photo;

	public PersonPhoto() {
		this.id = new PersonPhotoPK();
		this.person = new Person();
		id.setPhotoId(Hash.getId());
	}
	
	public PersonPhotoPK getId() {
		return id;
	}

	public void setId(PersonPhotoPK id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		this.id.setPersonId(person.getId());
	}

	public byte[] getPhoto() {
		return photo;
	}

	public void setPhoto(byte[] photo) {
		this.photo = photo;
	}
}