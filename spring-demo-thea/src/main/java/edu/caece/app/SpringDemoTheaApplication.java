package edu.caece.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.repository.IFotoRepositorio;
import edu.caece.app.repository.IFuncionRepositorio;
import edu.caece.app.repository.IRolRepositorio;
import edu.caece.app.repository.IUsuarioRepositorio;
import edu.caece.app.resources.LecturaExcel;

@SpringBootApplication
@ComponentScan(basePackages = { "edu.caece.app", 
								"edu.caece.app.controller", 
								"edu.caece.app.domain",
								"edu.caece.app.repository",
								"edu.caece.app.service" })

public class SpringDemoTheaApplication {
	
	protected final Logger log = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoTheaApplication.class, args);
		
		//LecturaExcel lecturaExcel = new LecturaExcel();
		//lecturaExcel.probar();
	}

	@Bean
	ApplicationRunner init(IUsuarioRepositorio usuarioRepositorio, 
			               IRolRepositorio rolRepositorio,
			               IPersonaRepositorio personaRepositorio,
			               IFuncionRepositorio funcionRepositorio,
			               IFotoRepositorio fotoRepositorio) throws Exception {
		return args -> {
	
			inicializarBD(usuarioRepositorio,
						  rolRepositorio,
						  personaRepositorio,
						  funcionRepositorio,
						  fotoRepositorio);
			log.info(Constantes.LOG_BBDD);
		};
	 }
	
	public void inicializarBD(IUsuarioRepositorio usuarioRepositorio, 
            				  IRolRepositorio rolRepositorio,
            				  IPersonaRepositorio personaRepositorio,
            				  IFuncionRepositorio funcionRepositorio,
            				  IFotoRepositorio fotoRepositorio) throws Exception {
		try {
			LecturaExcel lecturaExcel = new LecturaExcel();
			lecturaExcel.inicializarBD(usuarioRepositorio,
									   rolRepositorio,
									   personaRepositorio,
									   funcionRepositorio,
									   fotoRepositorio);
		} catch (Exception e) {
			throw new Exception ("method inicializarBD :: " + e.getMessage());
		}
	}

}
