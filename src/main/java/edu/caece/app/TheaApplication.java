package edu.caece.app;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import edu.caece.app.domain.Function;
import edu.caece.app.domain.Person;
import edu.caece.app.repository.FunctionRepository;
import edu.caece.app.repository.PersonRepository;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@ComponentScan(basePackages = { "edu.caece.app", "edu.caece.app.controller", "edu.caece.app.service" })
public class TheaApplication {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	public static void main(String[] args) {
		SpringApplication.run(TheaApplication.class, args);
	}

	@Bean
	@Order(Ordered.HIGHEST_PRECEDENCE)
	ApplicationRunner init(PersonRepository personRepository, FunctionRepository functionRepository) {
		
		return (args) -> {
			EntityManager em = entityManagerFactory.createEntityManager();
			em.getTransaction().begin();
//			Function alumno = new Function("alumno");
//			alumno = functionRepository.save(alumno);
			personRepository.save(new Person("Matias","Bava","11111111","Matricula001",true));
			personRepository.save(new Person("Javier","Michelson","11111112","Matricula002",true));
			personRepository.save(new Person("Maximiliano","Astengo","11111113","Matricula003",false));
			personRepository.save(new Person("Maria Natalia","Gonzalez","11111114","Matricula004",true));
			em.getTransaction().commit();
		};
	}

}
