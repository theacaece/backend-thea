package edu.caece.app.resources;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.exception.ConstraintViolationException;

import edu.caece.app.config.Hash;
import edu.caece.app.domain.Function;
import edu.caece.app.domain.Person;
import edu.caece.app.domain.Role;
import edu.caece.app.domain.User;
import edu.caece.app.repository.IFunctionRepository;
import edu.caece.app.repository.IPersonRepository;
import edu.caece.app.repository.IRoleRepository;
import edu.caece.app.repository.IUserRepository;

public class ExcelReader {

	// RUTA DENTRO DEL MISMO PROYECTO
	private static final String PATH = System.getProperty("user.dir"); // Obtiene Ruta del proyecto.
	private static final String RUTA_CSV = "/src/main/resources/DatosBD.xlsx";

	private XSSFWorkbook _worbook = null;
	private XSSFSheet _sheet = null;
	private FileInputStream _file;
	
	private static final int SOLAPA_ROLES = 0;
	private static final int SOLAPA_FUNCIONES = 1;
	private static final int SOLAPA_USUARIOS = 2;
	private static final int SOLAPA_PERSONAS = 3;

	private HashMap<Long, Role> _roles = new HashMap<Long, Role>();
	private HashMap<Long, Function> _functions = new HashMap<Long, Function>();

	
	private IUserRepository _userRepository;
	private IRoleRepository _roleRepository;
	private IPersonRepository _personRepository;
	private IFunctionRepository _functionRepository;

	public ExcelReader(IUserRepository userRepository, IRoleRepository roleRepository,
			IPersonRepository personRepository, IFunctionRepository functionRepository) {

		_userRepository = userRepository;
		_roleRepository = roleRepository;
		_personRepository = personRepository;
		_functionRepository = functionRepository;
		
	}

	public void inicializeDB() {
		try {
			readFile();
			processRoles();
			processUsers();
			processFunctions();
			processPersons();
			closeFile();
		} catch (Exception e) {
			System.out.print("method inicializarBD :: " + e.getMessage());
		}
	}

	private void readFile() {
		try {
			_file = new FileInputStream(new File(PATH + RUTA_CSV));
			_worbook = new XSSFWorkbook(_file);
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}
	
	private void closeFile() {
		try {
		_file.close();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private void processRoles() {
		try {
			readSheetRoles();
			saveRoles();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private void processUsers() {
		try {
			saveUsers(readSheetUsers());
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private void processFunctions() {
		try {

			readSheetFunctions();
			saveFunctions();
		} catch (Exception e) {
			System.out.print(e.getMessage());
		}
	}

	private void processPersons() {

		try {
			savePersons(readSheetPersons());
		} catch (Exception e) {
			e.getMessage();
		}
	}

	private ArrayList<User> readSheetUsers() {

		ArrayList<User> users = new ArrayList<User>(); // Creacion de Lista de Usuarios
		Iterator<Row> rowIterator = null; // Obtiene Todas las Filas de Excel
		Row fila;
		Iterator<Cell> cellIterator = null;
		Cell celda = null;

		User user = null;
		Role role = null;
		long id_role = 0;

		_sheet = _worbook.getSheetAt(SOLAPA_USUARIOS);
		rowIterator = _sheet.iterator();
		rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos

		while (rowIterator.hasNext()) { // Se Recorre Cada Fila Hasta el Final

			fila = rowIterator.next(); // Recorro Fila del Excel
			cellIterator = fila.cellIterator(); // Se Obtienen celdas de fila del ExcelI

			while (cellIterator.hasNext()) {

				user = new User(); // Creo Objeto Usuario
				celda = cellIterator.next(); // Leo Celda Nombre del Excel
				user.setFirstName(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda Apellido del Excel
				user.setLastName(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda Email del Excel
				user.setEmail(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda Usuario del Excel
				user.setUsername(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda Password del Excel
				user.setPassword(Hash.sha1(celda.getStringCellValue()));
				celda = cellIterator.next();// Leo Celda Rol del Excel
				id_role = (long) celda.getNumericCellValue();
				role = _roles.get(id_role);
				user.getRoles().add(role);
				users.add(user); // Agrego a Lista de Usuarios
			}
		}

		return users;
	}

	private void readSheetRoles() {

		Iterator<Row> rowIterator = null; // Obtiene Todas las Filas de Excel
		Row fila;
		Iterator<Cell> cellIterator = null;
		Cell celda = null;

		Role role = null;
		long idRole = 0;
		String name = "";

		_sheet = _worbook.getSheetAt(SOLAPA_ROLES);
		rowIterator = _sheet.iterator();
		rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos

		while (rowIterator.hasNext()) {

			fila = rowIterator.next(); // Recorro Fila del Excel
			cellIterator = fila.cellIterator(); // Se Obtienen celdas de fila del Excel

			while (cellIterator.hasNext()) { // Se Recorre Cada Celda de la fila del Excel

				celda = cellIterator.next(); // Leo Celda Id Rol
				idRole = (long) celda.getNumericCellValue();
				celda = cellIterator.next(); // Leo Celda Nombre Rol
				name = celda.getStringCellValue();
				role = new Role(idRole, name);
				_roles.put(role.getId(), role); // Agrego a Lista de Roles

			}
		}
	}

	private void readSheetFunctions() {

		Iterator<Row> rowIterator = null; // Obtiene Todas las Filas de Excel
		Row row = null;
		Cell celda = null;
		Iterator<Cell> cellIterator = null;
		long id = 0;
		String name = "";
		Function function = null;

		_sheet = _worbook.getSheetAt(SOLAPA_FUNCIONES);
		rowIterator = _sheet.iterator();
		rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos

		while (rowIterator.hasNext()) {

			row = rowIterator.next(); // Recorro Fila del Excel
			cellIterator = row.cellIterator(); // Se Obtienen celdas de fila del Excel

			while (cellIterator.hasNext()) {

				celda = cellIterator.next(); // Leo Celda Id Funcion
				id = (long) celda.getNumericCellValue();
				celda = cellIterator.next(); // Leo Celda Nombre Funcion
				name = celda.getStringCellValue();
				function = new Function(id, name);
				_functions.put(id, function); // Agrego a Lista de Usuarios

			}
		}
	}

	private ArrayList<Person> readSheetPersons() {

		ArrayList<Person> persons = new ArrayList<Person>(); // Creacion de Lista de Personas
		Person person = null;
		Iterator<Row> rowIterator = null; // Obtiene Todas las Filas de Excel
		Iterator<Cell> cellIterator = null;
		Cell celda = null;
		long id = 0;
		Function function;
		Row row;

		_sheet = _worbook.getSheetAt(SOLAPA_PERSONAS);
		rowIterator = _sheet.iterator();
		rowIterator.next(); // Con Esto Descarto Primera Fila con Titulos

		while (rowIterator.hasNext()) {

			row = rowIterator.next(); // Recorro Fila del Excel
			cellIterator = row.cellIterator(); // Se Obtienen celdas de fila del Excel

			while (cellIterator.hasNext()) {

				person = new Person(); // Creo Objeto Persona
				celda = cellIterator.next(); // Leo Celda Nombre del Excel
				person.setFirstName(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda Apellido del Excel
				person.setLastName(celda.getStringCellValue());
				celda = cellIterator.next(); // Leo Celda DNI del Excel
				person.setDni(Integer.parseInt(celda.getStringCellValue()));
				celda = cellIterator.next(); // Leo Funcion del Excel
				id = (long) celda.getNumericCellValue();
				function = _functions.get(id);
				person.getFunctions().add(function);
				celda = cellIterator.next(); // Leo Celda Matricula del Excel
				person.setRegistrationNumber(Integer.parseInt(celda.getStringCellValue()));

				persons.add(person);

			}
		}

		return persons;
	}

	private void saveUsers(ArrayList<User> users) throws Exception {
		try {
			for (User user : users) {
				_userRepository.save(user);
			}
			// usuarioRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception("ExcelReader.saveUsers :: " + e.getMessage());
		}
	}

	private void saveRoles() throws Exception {
		try {
			for (Role role : _roles.values()) {
				_roleRepository.save(role);
			}
			// rolRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception("ExcelReader.saveRoles :: " + e.getMessage());
		}
	}

	private void saveFunctions() throws Exception {
		try {
			for (Function funcion : _functions.values()) {
				_functionRepository.save(funcion);
			}
			// funcionRepositorio.findAll().forEach(System.out::println);
		} catch (Exception e) {
			throw new Exception("method guardarFunciones :: " + e.getMessage());
		}
	}

	private void savePersons(ArrayList<Person> persons) throws Exception {

		try {

			for (Person person : persons) {
				_personRepository.save(person);
			}

			_personRepository.findAll().forEach(System.out::println);

		} catch (ConstraintViolationException e) {
			throw new Exception("method guardarPersonas :: ConstraintViolationException :: " + e.getMessage());
		} catch (Exception e) {
			throw new Exception("method guardarDatosPersonas :: " + e.getMessage());
		}
	}
}
