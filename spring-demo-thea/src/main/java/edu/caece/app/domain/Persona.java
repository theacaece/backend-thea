package edu.caece.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import javax.persistence.Table;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity(name = "Persona")
@Table(name = "persona")
@DynamicUpdate
@Data
public class Persona implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "nombre", nullable = false)
  private String nombre;

  @Column(name = "apellido", nullable = false)
  private String apellido;

  @Column(name = "dni")
  private String dni;

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.EAGER)
  @JoinTable(name = "persona_funcion",
      joinColumns = @JoinColumn(name = "persona_dni", referencedColumnName = "dni"),
      inverseJoinColumns = @JoinColumn(name = "funcion_id", referencedColumnName = "id"))
  private List<Funcion> funciones;

  @Column(name = "matricula")
  private String matricula;

  @Column
  private boolean habilitado = false;

  public Persona() {
    this.funciones = new ArrayList<Funcion>();
  }

  public Persona(String nombre, String apellido, String dni, String matricula) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
    this.matricula = matricula;
    this.funciones = new ArrayList<Funcion>();
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public boolean estaHabilitado() {
    return habilitado;
  }

  public void setHabilitado(boolean entryAllowed) {
    this.habilitado = entryAllowed;
  }

  public String getNombreCompleto() {
    return String.join(" ", this.getNombre(), this.getApellido());
  }

  @Override
  public int hashCode() {
    return Objects.hash(dni, habilitado, nombre, id, apellido, matricula);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Persona other = (Persona) obj;
    return Objects.equals(dni, other.dni) && habilitado == other.habilitado
        && Objects.equals(nombre, other.nombre) && Objects.equals(id, other.id)
        && Objects.equals(apellido, other.apellido) && Objects.equals(matricula, other.matricula);
  }

}
