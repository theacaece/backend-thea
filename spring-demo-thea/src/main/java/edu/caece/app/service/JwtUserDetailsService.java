package edu.caece.app.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import edu.caece.app.domain.Usuario;
import edu.caece.app.repository.UsuarioRepositorio;

@Service
public class JwtUserDetailsService implements UserDetailsService {

  @Autowired
  private UsuarioRepositorio usuarioRepositorio;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Usuario usuario = usuarioRepositorio.findById(1L).get();
    UserBuilder builder = null;
    builder = org.springframework.security.core.userdetails.User.withUsername(username);
    builder.password(usuario.getPassword());
    builder.roles(usuario.getRolesSeparetedComma());

    return builder.build();
  }
}
