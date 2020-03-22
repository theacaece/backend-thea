package edu.caece.app.domain;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "Rol_Permiso")
@Table(name = "rol_permiso")
@Getter
@Setter
@Data
public class Rol_Permiso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id = 0;

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

}
