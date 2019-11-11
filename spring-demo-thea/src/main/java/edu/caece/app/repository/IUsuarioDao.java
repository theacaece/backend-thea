package edu.caece.app.repository;

import java.util.List;

import edu.caece.app.domain.User;

public interface IUsuarioDao {
	
	List<User> getUsuarios() throws Exception;

	User getUsuarioById(int usuarioId) throws Exception;
	
	void deleteUsuario(int usuarioId) throws Exception;
	
}