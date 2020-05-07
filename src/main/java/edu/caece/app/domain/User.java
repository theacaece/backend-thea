package edu.caece.app.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "users")
public class User extends Person {

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	/**
	 * TODO: SHA-256 password y mecanísmo para login de usuario con el valor
	 * calculado
	 */
	@Column(name = "password", nullable = false)
	private String password;

	@ManyToMany(cascade = { CascadeType.ALL }, fetch = FetchType.LAZY)
	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	// @JsonIgnore
	private List<Role> roles;

	public User() {
		this.roles = new ArrayList<Role>();
	}

	public User(String name, String... roles) {
		String[] rl = new String[2];
		this.username = name;

		this.roles = new ArrayList<Role>();

		for (int i = 0; i < roles.length; i++) {
			rl = roles[i].split("#");
			this.roles.add(new Role(Long.parseLong(rl[0]), rl[1].toUpperCase()));
		}

		this.roles.forEach(x -> x.getUsers().add(this));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
		this.roles.forEach(x -> x.getUsers().add(this));
	}

	@JsonIgnore
	public String[] getRolesToArray() {
		return this.roles.stream().toArray(String[]::new);
	}
}
