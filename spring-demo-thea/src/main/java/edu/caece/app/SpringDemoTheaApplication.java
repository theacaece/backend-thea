package edu.caece.app;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import edu.caece.app.config.Hash;
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

		List<Role> roles = new ArrayList<Role>();

		roles.add(new Role(1L, "ADMIN"));
		roles.add(new Role(2L, "USER"));

		repository_role.saveAll(roles);
		repository_role.findAll().forEach(x -> {

			System.out.print(x.getId() + " | " + x.getName());
			System.out.println();
			
		});

		return args -> {
			Stream.of("Francisco;Ferrari;jferrari;ff@gmail.com;ffff;1#admin",
					"Javier;Michelson;jmichelson;jm@gmail.com;jjjj;2#user",
					"Juan;Salinas;jsalinas;js@gmail.com;ssss;2#user,1#admin",
					"Pablo;Garcia;pgarcia;pg@gmail.com;gggg;1#admin").forEach(alumno -> {

						String[] datos = alumno.split(";");
						String[] datos_roles = datos[5].split(",");

						User user = new User(datos[2], datos_roles);
						user.setFirstName(datos[0]);
						user.setLastName(datos[1]);
						user.setUsername(datos[2]);
						user.setEmail(datos[3]);
						user.setPassword(Hash.sha1(datos[4]));

						repository.save(user);
					});

			repository.findAll().forEach(x -> {
				// System.out::println
				System.out.print(
						x.getId() + " | " + x.getUsername() + " | " + x.getEmail() + " | " + x.getPassword() + " | ");
				System.out.println();
			});
			repository_role.findAll().forEach(x -> {

				System.out.print(x.getId() + " | " + x.getName());
				System.out.println();

			});

			System.out.println();

			repository_role.findAll().forEach(x -> {

				System.out.print(x.getId() + " | " + x.getName());
				System.out.println();

			});
		};
	}
}
