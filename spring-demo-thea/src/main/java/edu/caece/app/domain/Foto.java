package edu.caece.app.domain;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity(name = "Foto")
@Table(name = "foto")
@Data
public class Foto implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private Integer id;
//
//  @Lob
//  @Column(name = "archivo")
//  private byte[] archivo;
//
//  @Column(name = "nombre_archivo")
//  private String nombreArchivo;
//
//  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
//  @JoinColumn(name = "persona_dni", referencedColumnName = "dni", nullable = false)
//  private Persona persona;
//
//  public Foto() {
//
//  }
//
//  public Foto(byte[] archivo, String idPersona) {
//    this.archivo = archivo;
//  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }
//
//  public byte[] getArchivo() {
//    return archivo;
//  }
//
//  public void setArchivo(byte[] archivo) {
//    this.archivo = archivo;
//  }
//
//  public String getNombreArchivo() {
//    return nombreArchivo;
//  }
//
//  public void setNombreArchivo(String nombreArchivo) {
//    this.nombreArchivo = nombreArchivo;
//  }
//
//  public Persona getPersona() {
//    return persona;
//  }
//
//  public void setPersona(Persona persona) {
//    this.persona = persona;
//  }

}