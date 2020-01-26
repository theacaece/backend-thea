package edu.caece.app.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.JoinColumn;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Usuario")
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
	
//	@OneToMany(fetch=FetchType.EAGER)
//	@JoinColumn(name = "username", nullable = false, insertable=false, updatable=false)
//    private Set<Acceso> accesos;

	@ManyToMany(cascade = { CascadeType.MERGE, 
							CascadeType.REMOVE, 
							CascadeType.REFRESH,
							CascadeType.DETACH }, 
							fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_rol", 
			   joinColumns = @JoinColumn(name = "usuario_id", referencedColumnName = "id"), 
			   inverseJoinColumns = @JoinColumn(name = "rol_id", referencedColumnName = "id"))
	private List<Rol> roles;

	public Usuario() {
		this.roles = new ArrayList<Rol>();
		//this.accesos = new HashSet<Acceso>();
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstName) {
		this.firstname = firstName;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastName) {
		this.lastname = lastName;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
		this.roles.forEach(x -> x.getUsuarios().add(this));
	}
	
	public void addRol(Rol rol) {
        roles.add(rol);
    }
 
    public void removeRol(Rol rol) {
    	roles.remove(rol);
    }

//	public Set<Acceso> getAccesos() {
//		return accesos;
//	}
//
//	public void setAccesos(Set<Acceso> accesos) {
//		this.accesos = accesos;
//	}
//	
//	public void addAcceso(Acceso acceso) {
//		accesos.add(acceso);
//    }
// 
//    public void removeAcceso(Acceso acceso) {
//    	accesos.remove(acceso);
//    }
    
    @JsonIgnore
	public String[] getRolesToArray() {
		String[] rl = new String[roles.size()];
		int i = 0;

		for (Rol r : roles) {
			rl[i] = r.getName();
			i++;
		}
		return rl;
	}

	public String toString () {
        String datosUsuario = this.id+this.firstname+this.lastname+this.email+this.username+this.password;
        return datosUsuario;
    }
    
}
