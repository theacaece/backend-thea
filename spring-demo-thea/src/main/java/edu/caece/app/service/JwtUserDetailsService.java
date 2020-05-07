package edu.caece.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.caece.app.domain.User;
import edu.caece.app.repository.IUserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService{

	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User usuario = userRepository.findByUsername(username);
		
		if(usuario == null)
			throw new UsernameNotFoundException("INVALID CREDENTIALS");
		
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(usuario.getPassword());
		builder.roles(usuario.getRolesToArray());
		
		return builder.build();
	}
}