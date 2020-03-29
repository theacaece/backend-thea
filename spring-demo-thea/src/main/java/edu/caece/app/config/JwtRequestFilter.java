package edu.caece.app.config;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import edu.caece.app.service.JwtUserDetailsService;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

  @Autowired(required = true)
  private JwtUserDetailsService jwtUserDetailsService;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain chain) throws ServletException, IOException {
    try {
      if (SecurityContextHolder.getContext().getAuthentication() == null) {
        final String requestTokenHeader = request.getHeader("Authorization");
        if (requestTokenHeader != null) {
          String jwtToken = requestTokenHeader;
          if (requestTokenHeader.startsWith("Bearer ")) { // Remuevo Bearer y obtengo el token
            jwtToken = requestTokenHeader.substring(7);
          }
          String username = jwtTokenUtil.getUsernameFromToken(jwtToken);
          if (username != null) { // Con TOKEN validado
            UserDetails userDetails = this.jwtUserDetailsService.loadUserByUsername(username);
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
              UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(
                  userDetails, null, userDetails.getAuthorities());
              token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
              SecurityContextHolder.getContext().setAuthentication(token); // Autentica
            }
          }
        }
      }
      chain.doFilter(request, response);
    } catch (Exception e) {
      logger.warn(e.getMessage());
    }
  }
}
