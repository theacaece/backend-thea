package edu.caece.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.when;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import edu.caece.app.config.Hash;
import edu.caece.app.config.JwtTokenUtil;
import edu.caece.app.controller.JwtAuthenticationController;
import edu.caece.app.controller.UsuarioController;
import edu.caece.app.domain.JwtRequest;
import edu.caece.app.domain.JwtResponse;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.UsuarioRepositorio;
import edu.caece.app.service.AutenticacionService;
import edu.caece.app.service.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringDemoTheaApplicationTests {

	@Autowired
	private AutenticacionService autenticationService;

	@Autowired
	private JwtAuthenticationController authenticationController;

	@Autowired
	private UsuarioController usuarioController;

	@MockBean
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService usuarioDetailService;

	@MockBean
	private UsuarioRepositorio usuarioRepositorio;

	
	@Test
	public void autenticationServiceTestOK() throws Exception {

		String username = "jlopez";
		String password = "JLopez2020";

		when(usuarioRepositorio.findByUsername(username))
				.thenReturn(new Usuario("jorge", "Lopez", "jlopez", Hash.sha1(password)));
		Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
		assertEquals(authentication, autenticationService.authenticate(authentication));
	}

	@Test
	public void autenticationServiceTestUsuarioInexistente() {

		String username = "jlopez";
		String password = "JLopez2020";

		try {
			when(usuarioRepositorio.findByUsername(username)).thenReturn(null);
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
			autenticationService.authenticate(authentication);

		} catch (UsernameNotFoundException ex) {

			assertEquals(Constantes.ERROR_USUARIO_INEXISTENTE, ex.getMessage());

		} catch (Exception ex) {

		}
	}

	@Test
	public void autenticationServiceTestErrorAutenticacion() {

		String username = "jlopez";
		String password = "JLopez2020";
		String password_bad = "JJlopez";

		try {
			when(usuarioRepositorio.findByUsername(username))
					.thenReturn(new Usuario("jorge", "Lopez", "jlopez", Hash.sha1(password_bad)));
			;
			Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
			autenticationService.authenticate(authentication);

		} catch (BadCredentialsException ex) {

			assertEquals(Constantes.ERROR_AUTENTICACION, ex.getMessage());

		} catch (Exception ex) {

		}
	}

	@Test
	public void autheticationControllerTestOK() {

		String username = "jlopez";
		String password = "JLopez2020";

		JwtRequest autenticacionRequest = new JwtRequest();
		autenticacionRequest.setUsername(username);
		autenticacionRequest.setPassword(password);
		
		try {
			when(usuarioRepositorio.findByUsername(username))
					.thenReturn(new Usuario("jorge", "Lopez", "jlopez", Hash.sha1(password), "ADMIN"));

			autenticationService.authenticate(new UsernamePasswordAuthenticationToken(
					autenticacionRequest.getUsername(), autenticacionRequest.getPassword()));

			
			UserDetails usuarioDetalle = usuarioDetailService
					.loadUserByUsername(autenticacionRequest.getUsername());
			
			
			when(jwtTokenUtil.generateToken(usuarioDetalle)).thenReturn("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9wZXoiLCJleHAiOjE1OTUwNTUxOTcsImlhdCI6MTU5NTAzNzE5NywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdfQ.mfQX9Z3znM0d9s2NIe6_W3b0BLwj9sa18zos5HAsi5zhc1arMYk620TCEzlWV_67pSs1SYYd1V1rLKyF8yiiqQ");

			String token = jwtTokenUtil.generateToken(usuarioDetalle);
			
			assertEquals(ResponseEntity.ok(new JwtResponse(token, usuarioDetalle)).getStatusCode().hashCode(),
					authenticationController.crearAutenticacionToken(autenticacionRequest).getStatusCode().hashCode());

		} catch (Exception e) {
			fail("Error: " + e.getMessage());
		}
	}
	
	@Test
	public void autheticationControllerTestError() {
		String username = "jlopez";
		String password = "JLopez2020";
		String pass_bad = "pass_erronea";

		JwtRequest autenticacionRequest = new JwtRequest();
		autenticacionRequest.setUsername(username);
		autenticacionRequest.setPassword(pass_bad);
		ResponseEntity<?> response = null;
		
		try {
			when(usuarioRepositorio.findByUsername(username))
					.thenReturn(new Usuario("jorge", "Lopez", "jlopez", Hash.sha1(password), "ADMIN"));

			UserDetails usuarioDetalle = usuarioDetailService
					.loadUserByUsername(autenticacionRequest.getUsername());
						
			when(jwtTokenUtil.generateToken(usuarioDetalle)).thenReturn("Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqbG9wZXoiLCJleHAiOjE1OTUwNTUxOTcsImlhdCI6MTU5NTAzNzE5NywiYXV0aG9yaXRpZXMiOlsiUk9MRV9BRE1JTiJdfQ.mfQX9Z3znM0d9s2NIe6_W3b0BLwj9sa18zos5HAsi5zhc1arMYk620TCEzlWV_67pSs1SYYd1V1rLKyF8yiiqQ");
			response = authenticationController.crearAutenticacionToken(autenticacionRequest);
			
			if(response.getStatusCode() == HttpStatus.NOT_FOUND)
				throw new Exception("Error en autenticacion");
			
		} catch (Exception e) {
			assertEquals(new ResponseEntity<>(Constantes.ERROR_AUTENTICACION, HttpStatus.NOT_FOUND), response);
		} finally {
			if(response.getStatusCode() == HttpStatus.OK)
				fail("Error en autheticationControllerTestError");
		}
	}
	
}
