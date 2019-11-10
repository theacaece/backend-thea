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
import edu.caece.app.domain.Person;
import edu.caece.app.domain.Role;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IPersonRepository;
import edu.caece.app.repository.IRoleRepository;
import edu.caece.app.repository.IUserRepository;

@SpringBootApplication
@ComponentScan(basePackages = { "edu.caece.app", 
								"edu.caece.app.controller", 
								"edu.caece.app.domain",
								"edu.caece.app.service" })

public class SpringDemoTheaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoTheaApplication.class, args);
	}

	@Bean
	ApplicationRunner init(IUserRepository userRepository, 
			               IRoleRepository roleRepository,
			               IPersonRepository personRepository) {

		List<Role> roles = new ArrayList<Role>();
		roles.add(new Role(1L, "ADMIN"));
		roles.add(new Role(2L, "USER"));
		roleRepository.saveAll(roles);
		
		String[] usuarios = {"Francisco;Ferrari;jferrari;ff@gmail.com;ffff;1#admin",
		                     "Javier;Michelson;jmichelson;jm@gmail.com;jjjj;2#user,1#admin",
		                     "Juan;Salinas;jsalinas;js@gmail.com;ssss;2#user,1#admin",
		                     "Pablo;Garcia;pgarcia;pg@gmail.com;gggg;1#admin"};

		return args -> {
			
			Stream.of(usuarios).forEach(alumno -> {

				String[] datos = alumno.split(";");
				String[] datos_roles = datos[5].split(",");
	
				User user = new User(datos[2], datos_roles);
				user.setFirstName(datos[0]);
				user.setLastName(datos[1]);
				user.setUsername(datos[2]);
				user.setEmail(datos[3]);
				user.setPassword(Hash.sha1(datos[4]));
	
				userRepository.save(user);
			});
			
			
			String[] personas = {"Francisco;Ferrari;dni33333333;ffff",
					             "Javier;Michelson;dni33333333;jjjj"};

			Stream.of(personas).forEach(persona -> {

				String[] datos = persona.split(";");

				Person person = new Person();
				person.setNombre(datos[0]);
				person.setApellido(datos[1]);
				person.setDni(datos[2]);
				person.setMatricula(datos[3]);
	
				personRepository.save(person);
			});

		userRepository.findAll().forEach(x -> {x.toString();});
		roleRepository.findAll().forEach(x -> {x.toString();});
		personRepository.findAll().forEach(x -> {x.toString();});

	};
 }
}
