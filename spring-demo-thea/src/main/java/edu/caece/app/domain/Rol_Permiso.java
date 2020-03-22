package edu.caece.app.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity
@Data
public class Rol_Permiso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "id_rol")
  private Integer id_rol;

  @Column(name = "id_permiso")
  private Integer id_permiso;

  public Rol_Permiso() {

  }

  public Rol_Permiso(Integer idRol, Integer idPersona) {
    this.id_rol = idRol;
    this.id_permiso = idPersona;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Integer getId_rol() {
    return id_rol;
  }

  public void setId_rol(Integer id_rol) {
    this.id_rol = id_rol;
  }

  public Integer getId_permiso() {
    return id_permiso;
  }

  public void setId_permiso(Integer id_permiso) {
    this.id_permiso = id_permiso;
  }

}
