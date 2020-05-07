package edu.caece.app;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import edu.caece.app.repository.FunctionRepository;
import edu.caece.app.repository.PersonRepository;
import edu.caece.app.repository.RoleRepository;
import edu.caece.app.repository.UserRepository;
import edu.caece.app.resources.ExcelReader;

@SpringBootApplication
@ComponentScan(basePackages = { "edu.caece.app", "edu.caece.app.controller", "edu.caece.app.service" })
public class SpringDemoTheaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringDemoTheaApplication.class, args);
	}

	@Bean
	ApplicationRunner init(UserRepository userRepository, RoleRepository roleRepository,
			PersonRepository personRepository, FunctionRepository functionRepository) {

		return args -> {
			
			ExcelReader datos = new ExcelReader(userRepository, roleRepository, personRepository, functionRepository);
			datos.inicializeDB();


			userRepository.findAll().forEach(x -> {
				System.out.print(
						x.getId() + " | " + x.getUsername() + " | " + x.getEmail() + " | " + x.getPassword() + " | ");
				System.out.println();
			});
			
			System.out.println();

			roleRepository.findAll().forEach(x -> {
				System.out.print(x.getId() + " | " + x.getName());
				System.out.println();

			});
		};
	}
}