package edu.caece.app.domain;

import java.io.Serializable;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private UserDetails userDetails;
	private String jwttoken;

	
	public UserDetails getUserDetails() {
		return userDetails;
	}

	public void setUserDetails(UserDetails userDetails) {
		this.userDetails = userDetails;
	}

	public String getJwttoken() {
		return jwttoken;
	}

	public void setJwttoken(String jwttoken) {
		this.jwttoken = jwttoken;
	}
	
	
	public JwtResponse(String jwttoken, UserDetails userDetail) {
		this.jwttoken = jwttoken;
		this.userDetails = userDetail;
	}
}
