package edu.caece.app.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.hibernate.annotations.GenericGenerator;
import lombok.Data;

@Entity
@Data
public class Usuario {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
  @GenericGenerator(name = "native", strategy = "native")
  @Column(name = "id", updatable = false, nullable = false)
  private long id;

  @Column(name = "firstname", nullable = false)
  private String firstname;

  @Column(name = "lastname", nullable = false)
  private String lastname;

  @Column(name = "username", nullable = false, unique = true)
  private String username;

  @Column(name = "email", nullable = false)
  private String email;

  @Column(name = "password", nullable = false)
  private String password;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getFirstname() {
    return firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getLastname() {
    return lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Rol> getRoles() {
    return roles;
  }

  public void setRoles(List<Rol> roles) {
    this.roles = roles;
  }

  @ManyToMany(
      cascade = {CascadeType.MERGE, CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH},
      fetch = FetchType.EAGER)
  @JoinTable(name = "usuario_rol",
      joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"),
      inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
  private List<Rol> roles;

  public Usuario() {
    this.roles = new ArrayList<Rol>();
  }

}
