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
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Data
public class Rol {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

  @Column(name = "name", nullable = false, unique = true)
  private String name;

  @ManyToMany(fetch = FetchType.EAGER, mappedBy = "roles")
  @JsonIgnore
  private List<Usuario> usuarios;

  public Rol() {
    this.usuarios = new ArrayList<Usuario>();
  }

  public Rol(String nombre) {
    this.name = nombre;
  }

}
