package edu.caece.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.caece.app.domain.User;

@Repository(value = "usuarioDao")
public class JPAUserDao implements IUsuarioDao {

    private EntityManager em = null;

    @PersistenceContext
    public void setEntityManager(EntityManager em) throws Exception {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<User> getUsuarios() throws Exception {
		return em.createQuery("SELECT u FROM usuario u").getResultList();
	}
	
	@Override
	public User getUsuarioById(int userId) throws Exception {
		return em.createQuery("SELECT u FROM usuario WHERE CAST(u.usuario AS Usuario) u WHERE u.id = :usuarioId", User.class).getSingleResult();
	}
	
	@Override
	public void deleteUsuario(int userId) throws Exception {
		em.createQuery("DELETE FROM usuario WHERE u.id = :usuarioId");
	}

	public User findByIds(int id) throws Exception {
		User usuario = null;
        try {
        	usuario = em.find(User.class, id);
        } catch (Exception e) {
			throw new Exception("method findById :: " + e.getMessage());
		}
        return usuario;
    }

	public void deleteUserById(int id) {
        em.createQuery("DELETE FROM usuario WHERE u.id = :userId");
    }

}