package edu.caece.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import edu.caece.app.repository.PersonaRepositorio;
import edu.caece.app.repository.RolRepositorio;
import edu.caece.app.repository.UsuarioRepositorio;
import edu.caece.app.resources.LecturaExcel;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.caece.app", "edu.caece.app.controller", "edu.caece.app.domain",
    "edu.caece.app.repository", "edu.caece.app.service"})
@Slf4j
public class SpringDemoTheaApplication {

  protected final Logger log = LoggerFactory.getLogger(getClass());

  public static void main(String[] args) throws Exception {
    try {
      SpringApplication.run(SpringDemoTheaApplication.class, args);
    } catch (Exception e) {
      throw new Exception("Error method main() :: " + e.getMessage());
    }
  }

  @Bean
  ApplicationRunner init(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio,
      PersonaRepositorio personaRepositorio) throws Exception {
    return args -> {
      inicializarBD(usuarioRepositorio, rolRepositorio, personaRepositorio);
    };
  }

  /**
   * INICIALIZA TODA LA BD CON LOS DATOS
   * 
   * @param usuarioRepositorio
   * @param rolRepositorio
   * @param personaRepositorio
   * @throws Exception
   */
  public boolean inicializarBD(UsuarioRepositorio usuarioRepositorio, RolRepositorio rolRepositorio,
      PersonaRepositorio personaRepositorio) throws Exception {
    try {
      LecturaExcel lecturaExcel = new LecturaExcel();
      lecturaExcel.obtenerDatosBD(usuarioRepositorio, rolRepositorio, personaRepositorio);
    } catch (Exception e) {
      throw new Exception("method inicializarBD() :: " + e.getMessage());
    }
    return true;
  }

}
