package edu.caece.app.domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class PersonPhoto {

	
	@ManyToOne
	@JoinColumn(name = "person_id", nullable = false, updatable = false)
	private Person person;
	
}
