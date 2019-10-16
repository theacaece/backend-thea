package edu.caece.app.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationManagerService implements AuthenticationManager  {

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		
		String user = authentication.getPrincipal().toString();
		String pass = authentication.getCredentials().toString();
		
		if("Admin".equals(user) && "passwd".equals(pass)) {
			return authentication;
		}
		else
		{
			throw new BadCredentialsException("INVALID CREDENTIALS");
		}
	}

}
