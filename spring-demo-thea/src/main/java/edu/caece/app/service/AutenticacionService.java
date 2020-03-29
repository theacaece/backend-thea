package edu.caece.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import edu.caece.app.Constantes;
import edu.caece.app.config.Hash;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.UsuarioRepositorio;

@Service
public class AutenticacionService implements AuthenticationManager {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  public Authentication authenticate(Authentication autenticacion) throws AuthenticationException {
    String usuario = autenticacion.getPrincipal().toString();
    String password = autenticacion.getCredentials().toString();
    Usuario usuarioBBDD = usuarioRepositorio.findByUsername(usuario);
    if (usuarioBBDD == null) {
      throw new UsernameNotFoundException(Constantes.ERROR_USUARIO_INEXISTENTE);
    } else {
      try {
        if (usuarioBBDD.getUsername().equals(usuario)
            && usuarioBBDD.getPassword().equals(Hash.sha1(password))) {
          return autenticacion;
        } else {
          throw new BadCredentialsException(Constantes.ERROR_AUTENTICACION);
        }
      } catch (Exception e) {
        throw new BadCredentialsException(Constantes.ERROR_AUTENTICACION);
      }
    }
  }
}
