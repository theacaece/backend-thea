package edu.caece.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import edu.caece.app.Constantes;
import edu.caece.app.config.JwtTokenUtil;
import edu.caece.app.domain.JwtRequest;
import edu.caece.app.domain.JwtResponse;
import edu.caece.app.service.AuthenticationManagerService;
import edu.caece.app.service.JwtUserDetailsService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class JwtAuthenticationController {

  @Autowired
  private AuthenticationManagerService authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService userDetailsService;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequest authenticationRequest)
      throws Exception {
    try {
      authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
      final UserDetails userDetails =
          userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
      final String token = jwtTokenUtil.generateToken(userDetails);
      return ResponseEntity.ok(new JwtResponse(token, userDetails));
    } catch (Exception e) {
      return new ResponseEntity<>(Constantes.ERROR_AUTENTICACION, HttpStatus.NOT_FOUND);
    }
  }

  private void authenticate(String username, String password) throws Exception {
    try {
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(username, password));
    } catch (DisabledException e) {
      throw new Exception(Constantes.ERROR_USUARIO_NO_AUTORIZADO, e);
    } catch (BadCredentialsException e) {
      throw new Exception(Constantes.ERROR_AUTENTICACION, e);
    }
  }
}
