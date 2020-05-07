package edu.caece.app.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import edu.caece.app.service.AuthenticationManagerService;
import edu.caece.app.service.JwtUserDetailsService;
import edu.caece.app.config.JwtTokenUtil;
import edu.caece.app.domain.JwtRequest;
import edu.caece.app.domain.JwtResponse;
import edu.caece.app.domain.User;
import edu.caece.app.domain.UserLog;
import edu.caece.app.repository.IUserLogRepository;
import edu.caece.app.repository.IUserRepository;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {
	
	@Autowired
	private AuthenticationManagerService authenticationManager;
	
	@Autowired
	private IUserLogRepository repository_logs;
	
	@Autowired
	private IUserRepository repository_users;
	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private JwtUserDetailsService userDetailsService;

	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {
		
		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);
		
		UserLog log = new UserLog();
		
		User user = repository_users.findByUsername(authenticationRequest.getUsername());
				
		log.setUser(user); //
		log.setAccessDate(new Date());
		log.setMessage("INGRESO AL SISTEMA");
		
		repository_logs.save(log);
		
		return ResponseEntity.ok(new JwtResponse(token, userDetails));
	
	}

	private void authenticate(String username, String password) throws Exception {
	
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		
		} catch (DisabledException e) {
			
			throw new Exception("USER_DISABLED", e);
		
		} catch (BadCredentialsException e) {
		
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}