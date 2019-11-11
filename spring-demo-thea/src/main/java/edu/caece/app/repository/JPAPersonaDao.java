package edu.caece.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.caece.app.domain.Persona;

@Repository(value = "personaDao")
public class JPAPersonaDao implements IPersonaDao {

    private EntityManager em = null;

    @PersistenceContext
    public void setEntityManager(EntityManager em) throws Exception {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Persona> getPersonas() throws Exception {
		return em.createQuery("SELECT p from persona p").getResultList();
	}

	@Override
	public Persona findById(int id) throws Exception {
		Persona person = null;
        try {
        	person = em.find(Persona.class, id);
        } catch (Exception e) {
			throw new Exception("method findById :: " + e.getMessage());
		}
        return person;
    }
}