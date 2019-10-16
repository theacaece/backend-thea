package edu.caece.app.service;

import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername("Admin");
		builder.password("passwd");
		builder.roles("user");
		
		return builder.build();
	}
}
