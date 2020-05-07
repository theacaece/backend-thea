package edu.caece.app.domain;

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

	@Column(nullable = false, unique = true)
	private String dni;

	@Column(nullable = false, unique = true)
	private String matricula;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;
	
	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "registration_number")
	private long registrationNumber;

	@OneToMany(mappedBy = "person", cascade = CascadeType.ALL)
	private Set<Photo> photos;

	@JoinTable(name = "persons_functions", joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "function_id", referencedColumnName = "id"))
	@ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	private Set<Function> functions;

	@Column
	private boolean entryAllowed = false;

	public Person() {
	}

	public Person(String firstName, String lastName, String dni, String matricula, boolean entryAllowed) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.matricula = matricula;
		this.entryAllowed = entryAllowed;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public boolean isEntryAllowed() {
		return entryAllowed;
	}

	public void setEntryAllowed(boolean entryAllowed) {
		this.entryAllowed = entryAllowed;
	}

	public String getFullName() {
		return String.join(" ", this.getFirstName(), this.getLastName());
	}
	
	public long getRegistrationNumber() {
		return registrationNumber;
	}

	public void setRegistrationNumber(long registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public Set<Photo> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<Photo> photos) {
		this.photos = photos;
	}

	public Set<Function> getFunctions() {
		return functions;
	}

	public void setFunctions(Set<Function> functions) {
		this.functions = functions;
	}

}
