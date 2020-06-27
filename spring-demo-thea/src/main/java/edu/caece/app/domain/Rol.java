package edu.caece.app.domain;

import java.util.ArrayList;
import java.util.List;
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

@Entity(name = "Rol")
@Table(name = "rol")
@Data
public class Rol {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

  @Column(name = "nombre", nullable = false, unique = true)
  private String nombre;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
  @JsonIgnore
  private List<Usuario> usuarios;

  public Rol() {
    usuarios = new ArrayList<Usuario>();
  }

  public Rol(String nombre) {
    this.usuarios = new ArrayList<Usuario>();
    this.nombre = nombre;
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

  public List<Usuario> getUsuarios() {
    return usuarios;
  }

  public void setUsuarios(List<Usuario> usuarios) {
    this.usuarios = usuarios;
  }

}
