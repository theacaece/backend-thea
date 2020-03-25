package edu.caece.app.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity(name = "Funcion")
@Table(name = "funcion")
@Data
public class Funcion implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @Column(name = "descripcion")
  private String descripcion;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "funciones")
  @JsonIgnore
  private List<Persona> personas;

  public Funcion() {
    this.personas = new ArrayList<Persona>();
  }

  public Funcion(String descripcion) {
    this.personas = new ArrayList<Persona>();
    this.descripcion = descripcion;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getDescripcion() {
    return descripcion;
  }

  public void setDescripcion(String descripcion) {
    this.descripcion = descripcion;
  }

  public List<Persona> getPersonas() {
    return personas;
  }

  public void setPersonas(List<Persona> personas) {
    this.personas = personas;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, descripcion);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Funcion other = (Funcion) obj;
    return Objects.equals(id, other.id) && Objects.equals(descripcion, other.descripcion);
  }

}
