package edu.caece.app;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import edu.caece.app.domain.Persona;
import edu.caece.app.domain.Foto;
import edu.caece.app.domain.Rol;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IPersonaRepositorio;
import edu.caece.app.repository.IFotoRepositorio;
import edu.caece.app.repository.IRolRepositorio;
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
	ApplicationRunner init(IUserRepository usuarioRepositorio, 
			               IRolRepositorio rolRepositorio,
			               IPersonaRepositorio personaRepositorio,
			               IFotoRepositorio fotoRepositorio) throws Exception {
		return args -> {
			
			List<Rol> roles = new ArrayList<Rol>();
			roles.add(new Rol(1L, "ADMIN"));
			roles.add(new Rol(2L, "USER"));
			rolRepositorio.saveAll(roles);

			inicializarDatosBD(usuarioRepositorio, 
							   rolRepositorio, 
							   personaRepositorio, 
							   fotoRepositorio);
			
		};
	 }

	 public void inicializarDatosBD(IUserRepository usuarioRepositorio, 
			 						IRolRepositorio rolRepositorio,
			 						IPersonaRepositorio personaRepositorio,
			 						IFotoRepositorio fotoRepositorio) throws Exception {
		crearTablaUsuarios(usuarioRepositorio);
		crearTablaPersonas(personaRepositorio);
		crearTablaFotos(fotoRepositorio);
	}
	 
 	private void crearTablaUsuarios(IUserRepository userRepository) throws Exception {
		try {
			ArrayList<User> usuarios = obtenerDatosUsuarios();
			guardarDatosUsuarios(userRepository, usuarios);
		} catch (Exception e) {
			throw new Exception("method crearTablaUsuarios" + e.getMessage());
		}
	}
	
	private void crearTablaPersonas(IPersonaRepositorio personRepository) throws Exception {
		try {
			ArrayList<Persona> personas = obtenerDatosPersonas();
			guardarDatosPersonas(personRepository, personas);
		} catch (Exception e) {
			throw new Exception("method crearTablaUsuarios" + e.getMessage());
		}
	}
	
	private void crearTablaFotos(IFotoRepositorio photoRepository) throws Exception {
		try {
			ArrayList<Foto> fotos = obtenerFotos();
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
	public static ArrayList<Persona> obtenerDatosPersonas() throws Exception {
		ArrayList<Persona> persons = null;
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
			}
			usuarioRepositorio.findAll().forEach(System.out::println);
			
		} catch (Exception e) {
			throw new Exception ("method guardarDatosUsuarios" + e.getMessage());
		}
	}
	
	public void guardarDatosPersonas(IPersonaRepositorio personRepository,
         ArrayList<Persona> persons) throws Exception {
		try {
			for (Persona person: persons) {
				personRepository.save(person);
			}
			personRepository.findAll().forEach(System.out::println);
			
		} catch (Exception e) {
			throw new Exception ("method guardarDatosPersonas" + e.getMessage());
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Foto> obtenerFotos() throws Exception {
		ArrayList<Foto> fotos = null;
		try {
			LecturaCarpeta lecturaCarpeta = new LecturaCarpeta();
			fotos = lecturaCarpeta.recorrerCarpeta();
		} catch (Exception e) {
			throw new Exception ("method obtenerFotos" + e.getMessage());
		} finally {
			return fotos;
		}
	}
	
	public void guardarFotos(IFotoRepositorio fotoRepositorio,
							 ArrayList<Foto> fotos) throws Exception {
		try {
			for (Foto foto: fotos) {
				fotoRepositorio.save(foto);
			}
			fotoRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception ("method guardarFotos" + e.getMessage());
		}
	}
}
