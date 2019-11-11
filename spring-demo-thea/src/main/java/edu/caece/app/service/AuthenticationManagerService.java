package edu.caece.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.caece.app.config.Hash;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IUserRepository;

@Service
public class AuthenticationManagerService implements AuthenticationManager {

	@Autowired
	private IUserRepository usuarioRepositorio;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String username = authentication.getPrincipal().toString();
		String password = authentication.getCredentials().toString();

		User user = usuarioRepositorio.findByUsername(username);

		if (user == null)
			throw new UsernameNotFoundException("INVALID CREDENTIALS");

		if (user.getUsername().equals(username) && user.getPassword().equals(Hash.sha1(password))) {
			return authentication;
		} else {
			throw new BadCredentialsException("INVALID CREDENTIALS");
		}
	}
}