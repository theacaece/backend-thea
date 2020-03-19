package edu.caece.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import edu.caece.app.repository.IFotoRepositorio;
import edu.caece.app.repository.IFuncionRepositorio;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.repository.IRegistroRepositorio;
import edu.caece.app.repository.IRolRepositorio;
import edu.caece.app.repository.IUsuarioRepositorio;
import edu.caece.app.resources.LecturaExcel;

@SpringBootApplication
@ComponentScan(basePackages = {"edu.caece.app", "edu.caece.app.controller", "edu.caece.app.domain",
    "edu.caece.app.repository", "edu.caece.app.service"})

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
  ApplicationRunner init(IUsuarioRepositorio usuarioRepositorio, IRolRepositorio rolRepositorio,
      IPersonaRepositorio personaRepositorio, IFuncionRepositorio funcionRepositorio,
      IRegistroRepositorio registroRepositorio, IFotoRepositorio fotoRepositorio) throws Exception {
    return args -> {
      inicializarBD(usuarioRepositorio, rolRepositorio, personaRepositorio, funcionRepositorio,
          registroRepositorio, fotoRepositorio);
    };
  }

  /**
   * INICIALIZA TODA LA BD CON LOS DATOS
   * 
   * @param usuarioRepositorio
   * @param rolRepositorio
   * @param personaRepositorio
   * @param funcionRepositorio
   * @param registroRepositorio
   * @param fotoRepositorio
   * @throws Exception
   */
  public void inicializarBD(IUsuarioRepositorio usuarioRepositorio, IRolRepositorio rolRepositorio,
      IPersonaRepositorio personaRepositorio, IFuncionRepositorio funcionRepositorio,
      IRegistroRepositorio registroRepositorio, IFotoRepositorio fotoRepositorio) throws Exception {
    try {
      LecturaExcel lecturaExcel = new LecturaExcel();
      lecturaExcel.obtenerDatosBD(usuarioRepositorio, rolRepositorio, personaRepositorio,
          funcionRepositorio, registroRepositorio, fotoRepositorio);
      System.out.print("Fin method inicializarBD() ");
    } catch (Exception e) {
      throw new Exception("method inicializarBD() :: " + e.getMessage());
    }
  }

}
