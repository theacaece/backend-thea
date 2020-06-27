package edu.caece.app.domain;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

  @Column(name = "matricula")
  private String matricula;

  @Column(name = "habilitado")
  private boolean habilitado = true;

  public Persona() {}

  public Persona(String nombre, String apellido, String dni, String matricula) {
    this.nombre = nombre;
    this.apellido = apellido;
    this.dni = dni;
    this.matricula = matricula;
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

  public boolean isHabilitado() {
    return habilitado;
  }

  public void setHabilitado(boolean habilitado) {
    this.habilitado = habilitado;
  }

  public String getNombreCompleto() {
    return String.join(" ", nombre, apellido);
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
