package edu.caece.app.domain;

import java.util.HashSet;
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
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "firstName", nullable = false)
	private String firstName;

	@Column(name = "lastName", nullable = false)
	private String lastName;

	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Transient
	private UserPhoto photo;

	@OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
	private Set<UserLog> logs;

	@JoinTable(name = "users_roles", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"))
	@ManyToMany(cascade = { CascadeType.REMOVE, CascadeType.REFRESH, CascadeType.DETACH }, fetch = FetchType.EAGER)
	private Set<Role> roles;

	public User() {
		this.roles = new HashSet<Role>();
		this.photo = new UserPhoto();
	}

	public User(String name, String... roles) {

		String[] rl = new String[2];
		this.roles = new HashSet<Role>();
		this.photo = new UserPhoto();
		this.username = name;

		for (int i = 0; i < roles.length; i++) {
			rl = roles[i].split("#");
			this.roles.add(new Role(Long.parseLong(rl[0]), rl[1].toUpperCase()));
		}
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
		// this.roles.forEach(x -> x.getUsers().add(this));
	}

	public UserPhoto getPhoto() {
		return photo;
	}

	public void setPhoto(UserPhoto photo) {

		this.photo = photo;
	}

	public Set<UserLog> getLogs() {
		return logs;
	}

	public void setLogs(Set<UserLog> logs) {
		this.logs = logs;
	}

	@JsonIgnore
	public String[] getRolesToArray() {

		String[] rl = new String[roles.size()];
		int i = 0;

		for (Role r : roles) {
			rl[i] = r.getName();
			i++;
		}

		return rl;
	}
}
