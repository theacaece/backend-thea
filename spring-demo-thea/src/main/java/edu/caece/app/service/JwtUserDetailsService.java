package edu.caece.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import edu.caece.app.Constantes;
import edu.caece.app.domain.Acceso;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.AccesoRepositorio;
import edu.caece.app.repository.UsuarioRepositorio;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;
  @Autowired
  private AccesoRepositorio accesoRepositorio;

  @Override
  public UserDetails loadUserByUsername(String _usuario) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepositorio.findByUsername(_usuario);
    if (usuario == null)
      throw new UsernameNotFoundException(Constantes.ERROR_USUARIO_INEXISTENTE);
    UserBuilder builder = org.springframework.security.core.userdetails.User.withUsername(_usuario);
    builder.password(usuario.getPassword());
    builder.roles(usuario.getRolesToArray());
    Acceso acceso = new Acceso(usuario.getUsername());
    accesoRepositorio.save(acceso);
    return builder.build();
  }
}
