package edu.caece.app;

import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import edu.caece.app.domain.Role;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IRoleRepository;
import edu.caece.app.repository.IUserRepository;

@SpringBootApplication
@ComponentScan(basePackages = { "edu.caece.app", "edu.caece.app.controller", "edu.caece.app.service" })
public class SpringDemoTheaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoTheaApplication.class, args);
	}

	@Bean
	ApplicationRunner init(IUserRepository repository, IRoleRepository repository_role) {
		return args -> {
			Stream.of("Francisco Ferrari;ff@gmail.com;ffff;admin", "Javier Michelson;jm@gmail.com;jjjj;user|admin",
					"Juan Salinas;js@gmail.com;ssss;user", "Pablo Garcia;pg@gmail.com;gggg;admin").forEach(alumno -> {

						String[] datos = alumno.split(";");
						String[] datos_roles = datos[3].split("|");

											
						User user = new User(datos[0], datos_roles);
						user.setName(datos[0]);
						user.setEmail(datos[1]);
						user.setPassword(datos[2]);

						repository.save(user);
					});

			repository.findAll().forEach(x -> {
					//System.out::println
					System.out.print(x.getId() + " | " + x.getName() + " | " + x.getEmail() + " | " + x.getPassword() + " | ");
					System.out.println();
			});

		};
	}
}
