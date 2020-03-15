package edu.caece.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import edu.caece.app.config.Hash;

@Entity
@Table(name = "persons_log")
public class PersonLog {

	@EmbeddedId
	private PersonLogPK id;
	
	@ManyToOne
	@MapsId("person_id")
	@JoinColumn(name = "person_id")
	private Person person;
		
	@Column(name = "access_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessDate;

	@Column(name = "message", nullable = true, length = 150)
	private String message;
		
	public PersonLog() {
		this.id = new PersonLogPK(); 
		this.person = new Person();
		id.setLogId(Hash.getId());
	}

	public PersonLogPK getId() {
		return id;
	}

	public void setId(PersonLogPK id) {
		this.id = id;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
		this.id.setPersonId(person.getId());
	}

	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
