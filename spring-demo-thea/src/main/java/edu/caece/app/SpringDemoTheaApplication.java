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
import edu.caece.app.domain.Photo;
import edu.caece.app.domain.Role;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IPersonRepository;
import edu.caece.app.repository.IPhotoRepository;
import edu.caece.app.repository.IRoleRepository;
import edu.caece.app.repository.IUserRepository;
import edu.caece.app.resources.LecturaCarpeta;
import edu.caece.app.resources.LecturaExcel;

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
			               IPersonRepository personRepository,
			               IPhotoRepository photoRepository) {

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
		
		inicializarDatosBD(userRepository, personRepository, photoRepository);

	};
 }

	 public void inicializarDatosBD(IUserRepository userRepository, 
				                    IPersonRepository personRepository,
				                    IPhotoRepository photoRepository) throws Exception {
		 // crearTablaUsuarios(userRepository);
		 // crearTablaPersonas(personRepository);
		crearTablaFotos(photoRepository);
	}
	 
 	private void crearTablaUsuarios(IUserRepository userRepository) throws Exception {
		try {
			ArrayList<User> usuarios = obtenerDatosUsuarios();
			guardarDatosUsuarios(userRepository, usuarios);
		} catch (Exception e) {
			throw new Exception("method crearTablaUsuarios" + e.getMessage());
		}
	}
	
	private void crearTablaPersonas(IPersonRepository personRepository) throws Exception {
		try {
			ArrayList<Person> personas = obtenerDatosPersonas();
			guardarDatosPersonas(personRepository, personas);
		} catch (Exception e) {
			throw new Exception("method crearTablaUsuarios" + e.getMessage());
		}
	}
	
	private void crearTablaFotos(IPhotoRepository photoRepository) throws Exception {
		try {
			ArrayList<Photo> fotos = obtenerFotos();
			guardarFotos(photoRepository, fotos);
		} catch (Exception e) {
			throw new Exception("method crearTablaFotos" + e.getMessage());
		}
	}

	@SuppressWarnings("finally")
	public static ArrayList<User> obtenerDatosUsuarios() throws Exception {
		ArrayList<User> usuarios = null;
		try {
			LecturaExcel lecturaExcel = new LecturaExcel();
			usuarios = lecturaExcel.obtenerUsuarios();
		} catch (Exception e) {
			throw new Exception ("method obtenerFotos" + e.getMessage());
		} finally {
			return usuarios;
		}
	}
	
	@SuppressWarnings("finally")
	public static ArrayList<Person> obtenerDatosPersonas() throws Exception {
		ArrayList<Person> persons = null;
		try {
			LecturaExcel lecturaExcel = new LecturaExcel();
			persons = lecturaExcel.obtenerPersonas();
		} catch (Exception e) {
			throw new Exception ("method obtenerDatosPersonas" + e.getMessage());
		} finally {
			return persons;
		}
	}

	public void guardarDatosUsuarios(IUserRepository usuarioRepositorio,
			                         ArrayList<User> users) throws Exception {
		try {
			for (User user: users) {
				usuarioRepositorio.save(user);
				//Usuario usu = usuarioRepositorio.findById(usuario.getId());
				//usuarioRepositorio.deleteUsuarioById(usuario.getId());
				//System.out.println(usu.toString());
			}
			usuarioRepositorio.findAll().forEach(System.out::println);
			
		} catch (Exception e) {
			throw new Exception ("method guardarDatosUsuarios" + e.getMessage());
		}
	}
	
	public void guardarDatosPersonas(IPersonRepository personRepository,
         ArrayList<Person> persons) throws Exception {
		try {
			for (Person person: persons) {
				personRepository.save(person);
			}
			personRepository.findAll().forEach(System.out::println);
			
		} catch (Exception e) {
			throw new Exception ("method guardarDatosPersonas" + e.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Photo> obtenerFotos() throws Exception {
		ArrayList<Photo> fotos = null;
		try {
			LecturaCarpeta lecturaCarpeta = new LecturaCarpeta();
			fotos = lecturaCarpeta.recorrerCarpeta();
		} catch (Exception e) {
			throw new Exception ("method obtenerFotos" + e.getMessage());
		} finally {
			return fotos;
		}
	}
	
	public void guardarFotos(IPhotoRepository fotoRepositorio,
							 ArrayList<Photo> fotos) throws Exception {
		try {
			for (Photo foto: fotos) {
				fotoRepositorio.save(foto);
			}
			fotoRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception ("method guardarFotos" + e.getMessage());
		}
	}
	
	public static void probar() throws Exception {
		try {
			
			LecturaExcel lecturaExcel = new LecturaExcel();
			lecturaExcel.obtenerUsuarios();
			lecturaExcel.obtenerPersonas();
			
			LecturaCarpeta lecturaCarpeta = new LecturaCarpeta();
			lecturaCarpeta.recorrerCarpeta();
			
		} catch (Exception e) {
			throw new Exception ("method probar" + e.getMessage());
		}
	}
}
