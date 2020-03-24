package edu.caece.app.domain;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity(name = "Acceso")
@Table(name = "acceso")
@Data
public class Acceso implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;

  @Column(name = "usuario")
  private String usuario;

  @Column(name = "fechaAcceso", nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date fechaAcceso;

  public Acceso() {

  }

  public Acceso(String usuario) {
    this.usuario = usuario;
    this.fechaAcceso = new Date();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUsuario() {
    return usuario;
  }

  public void setUsuario(String usuario) {
    this.usuario = usuario;
  }

  public Date getFechaAcceso() {
    return fechaAcceso;
  }

  public void setFechaAcceso(Date fechaAcceso) {
    this.fechaAcceso = fechaAcceso;
  }

}
