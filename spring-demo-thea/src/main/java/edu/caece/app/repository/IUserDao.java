package edu.caece.app.repository;

import java.util.List;

import edu.caece.app.domain.User;

public interface IUserDao {
	
	List<User> getUsers() throws Exception;

	User getUserById(int userId) throws Exception;
	
	void deleteUser(int userId) throws Exception;
	
}