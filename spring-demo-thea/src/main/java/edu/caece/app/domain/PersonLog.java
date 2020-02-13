package edu.caece.app.domain;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "persons_log")
public class PersonLog {
	
	@EmbeddedId
	private PersonLogPK id;	
	
	@Column(name = "user_message", nullable = true, length = 150)
	private String message;
		
}
