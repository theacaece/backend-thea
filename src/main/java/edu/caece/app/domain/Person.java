package edu.caece.app.domain;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;

	@Column(nullable = false)
	private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false, unique = true)
	private String dni;
//	
//	@ManyToOne(targetEntity = Function.class)
//	@Cascade(CascadeType.ALL)
//	private Function function;
//	
	@Column(nullable = false, unique = true)
	private String matricula;

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

	@Override
	public int hashCode() {
		return Objects.hash(dni, entryAllowed, firstName, id, lastName, matricula);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		return Objects.equals(dni, other.dni) && entryAllowed == other.entryAllowed
				&& Objects.equals(firstName, other.firstName) && Objects.equals(id, other.id)
				&& Objects.equals(lastName, other.lastName) && Objects.equals(matricula, other.matricula);
	}

	@Override
	public String toString() {
		return String.format("Person [id=%s, firstName=%s, lastName=%s, dni=%s, matricula=%s, entryAllowed=%s]", id,
				firstName, lastName, dni, matricula, entryAllowed);
	}

}
