package edu.caece.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import edu.caece.app.service.AutenticacionService;
import edu.caece.app.service.JwtUserDetailsService;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@Slf4j
public class JwtAuthenticationController {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  @Autowired
  private AutenticacionService authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private JwtUserDetailsService usuarioDetalleServicio;

  @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
  public ResponseEntity<?> crearAutenticacionToken(@RequestBody JwtRequest autenticacionRequest)
      throws Exception {
    log.info(Constantes.INFO_TOKEN);
    try {
      autenticar(autenticacionRequest.getUsername(), autenticacionRequest.getPassword());
      final UserDetails usuarioDetalle =
          usuarioDetalleServicio.loadUserByUsername(autenticacionRequest.getUsername());
      final String token = jwtTokenUtil.generateToken(usuarioDetalle);
      return ResponseEntity.ok(new JwtResponse(token, usuarioDetalle));
    } catch (Exception e) {
      return new ResponseEntity<>(Constantes.ERROR_AUTENTICACION, HttpStatus.NOT_FOUND);
    }
  }

  private void autenticar(String usuario, String password) throws Exception {
    try {
      log.info(Constantes.INFO_AUTENTICACION);
      authenticationManager
          .authenticate(new UsernamePasswordAuthenticationToken(usuario, password));
    } catch (DisabledException e) {
      throw new Exception(Constantes.ERROR_USUARIO_NO_AUTORIZADO, e);
    } catch (BadCredentialsException e) {
      throw new Exception(Constantes.ERROR_AUTENTICACION, e);
    } catch (Exception e) {
      throw new Exception(Constantes.ERROR_AUTENTICACION, e);
    }
  }

}
