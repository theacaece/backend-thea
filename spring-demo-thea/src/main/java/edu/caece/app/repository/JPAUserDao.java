package edu.caece.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.caece.app.domain.User;

@Repository(value = "userDao")
public class JPAUserDao implements IUserDao {

    private EntityManager em = null;

    @PersistenceContext
    public void setEntityManager(EntityManager em) throws Exception {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsers() throws Exception {
		return em.createQuery("SELECT u FROM users u").getResultList();
	}
	
	@Override
	public User getUserById(int userId) throws Exception {
		return em.createQuery("SELECT u FROM users WHERE CAST(u.usuario AS User) u WHERE u.id = :usuarioId", User.class).getSingleResult();
	}
	
	@Override
	public void deleteUser(int userId) throws Exception {
		em.createQuery("DELETE FROM users WHERE u.id = :usuarioId");
	}
	
	@Override
	public User findById(int id) throws Exception {
		User usuario = null;
        try {
        	usuario = em.find(User.class, id);
        } catch (Exception e) {
			throw new Exception("method findById :: " + e.getMessage());
		}
        return usuario;
    }

	public void deleteUserById(int id) {
        em.createQuery("DELETE FROM users WHERE u.id = :userId");
    }

}