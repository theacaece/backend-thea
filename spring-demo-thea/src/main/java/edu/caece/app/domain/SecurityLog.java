package edu.caece.app.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SecurityLog {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@ManyToOne
	private Persona person;
	
	@Column
	private String module;
	
	@Column
	private EventType eventType;
	
	@Column
	private String message;
	
	@Column
	private Date timestamp;
	
	public SecurityLog() {
		// Default value for timestamp
		this.timestamp = new Date();
	}

	public SecurityLog(String message, EventType eventType) {
		this(message, null, eventType);
	}
	
	public SecurityLog(String module, String message, EventType eventType) {
		this(module, message, null, eventType);
	}

	public SecurityLog(String module, String message, Persona person, EventType eventType) {
		this();
		this.person = person;
		this.module = module;
		this.message = message;
		this.eventType = eventType;
	}
	
}
