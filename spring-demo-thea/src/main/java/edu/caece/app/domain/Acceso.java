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

  @Column(name = "fecha_acceso")
  private String fechaAcceso;

  public Acceso() {

  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFechaAcceso() {
    return fechaAcceso;
  }

  public void setFechaAcceso(String fechaAcceso) {
    this.fechaAcceso = fechaAcceso;
  }

}
