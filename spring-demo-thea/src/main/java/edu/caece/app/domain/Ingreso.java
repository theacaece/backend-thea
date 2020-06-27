package edu.caece.app.domain;

import java.io.Serializable;
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
public class Ingreso implements Serializable {

	private static final long serialVersionUID = 6307385613459212248L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column
	private Long id;
	
	@ManyToOne
	private Persona persona;
		
	@Column
	private TipoEvento tipoEvento;
	
	@Column
	private String mensaje;
	
	@Column
	private Date timestamp;
	
	public Ingreso() {
		this.timestamp = new Date();
	}
	
	public Ingreso(Persona persona, TipoEvento tipoEvento, String mensaje) {
		this.persona = persona;
		this.tipoEvento = tipoEvento;
		this.mensaje = mensaje;
		this.timestamp = new Date();
	}

}
