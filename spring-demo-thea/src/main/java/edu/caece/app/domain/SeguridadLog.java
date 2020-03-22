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
  private EventType eventType;

  @Column
  private String mensaje;

  @Column
  private Date timestamp;

  public SeguridadLog() {
    this.timestamp = new Date();
  }

  public SeguridadLog(String message, EventType eventType) {
    this(message, null, eventType);
  }

  public SeguridadLog(String module, String message, EventType eventType) {
    this(module, message, null, eventType);
  }

  public SeguridadLog(String modelo, String mensaje, Persona persona, EventType eventType) {
    this();
    this.persona = persona;
    this.modulo = modelo;
    this.mensaje = mensaje;
    this.eventType = eventType;
  }

}
