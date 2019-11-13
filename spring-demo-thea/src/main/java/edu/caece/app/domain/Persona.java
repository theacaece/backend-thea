package edu.caece.app.domain;
import java.io.Serializable;
import java.util.ArrayList;
//import java.util.ArrayList;
//import java.util.List;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

@Entity(name = "Persona")
@Table(name="persona") 
@DynamicUpdate
public class Persona implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "id", updatable = false, nullable = false)
	private long id;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "apellido", nullable = false)
	private String apellido;

	@Column(name = "dni", nullable = false)
	private String dni;

	@ManyToMany(cascade = { CascadeType.MERGE, 
							CascadeType.REMOVE, 
							CascadeType.REFRESH,
							CascadeType.DETACH }, 
							fetch = FetchType.EAGER)
	@JoinTable(name = "persona_funcion", 
	joinColumns = @JoinColumn(name = "persona_id", referencedColumnName = "id"), 
	inverseJoinColumns = @JoinColumn(name = "funcion_id", referencedColumnName = "id"))
	private List<Funcion> funciones;

	@Column(name = "matricula")
	private String matricula;
	
	public Persona() {
		this.funciones = new ArrayList<Funcion>();
	}
	
	public Persona(String nombre,
			       String apellido,
			       String dni,
			       String matricula) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.dni = dni;
		this.matricula = matricula;
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
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
	
	public List<Funcion> getFunciones() {
		return funciones;
	}

	public void setFunciones(List<Funcion> funciones) {
		this.funciones = funciones;
	}
	
	public void addFuncion(Funcion funcion) {
		funciones.add(funcion);
    }
 
    public void removeFuncion(Funcion funcion) {
    	funciones.remove(funcion);
    }
	
	public String toString (){
        String datosPersona = "Persona::" + 
        					  nombre + ":" + 
        					  apellido + ":" + 
        					  dni + ":" + 
        					  matricula;
        return datosPersona;
    }

}
