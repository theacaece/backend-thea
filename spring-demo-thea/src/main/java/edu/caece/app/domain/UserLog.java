package edu.caece.app.domain;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

import edu.caece.app.config.Hash;

@Entity
@Table(name = "users_log")
public class UserLog {

	@EmbeddedId
	private UserLogPK id;
	
	@ManyToOne
	@MapsId("user_id")
	@JoinColumn(name = "user_id")
	@JsonIgnore
	private User user;
	
	@Column(name = "message", nullable = true, length = 150)
	private String message;
	
	@Column(name = "access_date")
	@Temporal(TemporalType.TIMESTAMP)
	private Date accessDate;	
	
	public UserLog() {
		this.id = new UserLogPK();
		this.user = new User();
		id.setLogId(Hash.getId());
	}

	public UserLogPK getId() {
		return id;
	}

	public void setId(UserLogPK id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		this.id.setUserId(user.getId());
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Date getAccessDate() {
		return accessDate;
	}

	public void setAccessDate(Date accessDate) {
		this.accessDate = accessDate;
	}
}
