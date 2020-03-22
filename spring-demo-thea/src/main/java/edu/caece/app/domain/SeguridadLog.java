package edu.caece.app.domain;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class SeguridadLog {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column
  private Long id;

  @ManyToOne
  private Persona persona;

  @Column
  private String modulo;

  @Column
  private TipoEvento tipoEvento;

  @Column
  private String mensaje;

  @Column
  private Date timestamp;

  public SeguridadLog() {
    this.timestamp = new Date();
  }

  public SeguridadLog(String mensaje, TipoEvento tipoEnvento) {
    this(mensaje, null, tipoEnvento);
  }

  public SeguridadLog(String modulo, String mensaje, TipoEvento tipoEvento) {
    this(modulo, mensaje, null, tipoEvento);
  }

  public SeguridadLog(String modulo, String mensaje, Persona persona, TipoEvento tipoEvento) {
    this();
    this.persona = persona;
    this.modulo = modulo;
    this.mensaje = mensaje;
    this.tipoEvento = tipoEvento;
  }

}
