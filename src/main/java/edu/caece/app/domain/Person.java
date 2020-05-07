package edu.caece.app.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private Set<PersonPhoto> photos;
	
	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private Set<PersonLog> registrations;

	@JoinTable(name = "persons_functions", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "function_id", referencedColumnName = "id"))
	@ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH,
			CascadeType.DETACH }, fetch = FetchType.EAGER)
	private Set<Function> functions;
	
	public Person() {
		this.photos = new HashSet<PersonPhoto>();
		this.registrations = new HashSet<PersonLog>();
		this.functions = new HashSet<Function>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public int getDni() {
		return dni;
	}

	public void setDni(int dni) {
		this.dni = dni;
	}

	public long getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(long registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Set<PersonPhoto> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<PersonPhoto> photos) {
		this.photos = photos;
	}

	public Set<PersonLog> getRegistrations() {
		return registrations;
	}

	public void setRegistrations(Set<PersonLog> registrations) {
		this.registrations = registrations;
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

}
