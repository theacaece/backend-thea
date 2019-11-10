package edu.caece.app.domain;
import java.io.Serializable;

import javax.persistence.*;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name="Person") 
public class Person implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "dni", nullable = false)
	private String dni;

	@ManyToOne
    @JoinColumn(name = "id_funcion")
    private Position funcion;
	
	@Column(name = "matricula")
	private String matricula;
	
	public Person() {
		
	}
	
	public Person(String firstName,
			       String lastName,
			       String dni,
			       String matricula) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.dni = dni;
		this.matricula = matricula;
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
	
	public String toString (){
        String datosPersona = "Persona::" + 
        					  firstName + ":" + 
        					  lastName + ":" + 
        					  dni + ":" + 
        					  matricula;
        return datosPersona;
    }

}
