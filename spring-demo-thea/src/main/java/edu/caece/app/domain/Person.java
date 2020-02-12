package edu.caece.app.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "persons")
public class Person {
	
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "firstName", nullable = false)
	private String firstName;
	
	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "dni", nullable = false)
	private int dni;
	
	@Column(name="registration_number")	
	private long registrationNumber;
	
	
	@OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL)
	private Set<PersonPhoto> photos;
	
	
	private Set<PersonLog> registrations;
	
	
	private Set<Function> functions;
	
	
}
