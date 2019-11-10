package edu.caece.app.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import edu.caece.app.domain.Person;
import edu.caece.app.domain.User;

@Repository(value = "personaDao")
public class JPAPersonDao implements IPersonDao {

    private EntityManager em = null;

    @PersistenceContext
    public void setEntityManager(EntityManager em) throws Exception {
        this.em = em;
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getPersonas() throws Exception {
		return em.createQuery("SELECT p from person p").getResultList();
	}

	@Override
	public Person findById(int id) throws Exception {
		Person person = null;
        try {
        	person = em.find(Person.class, id);
        } catch (Exception e) {
			throw new Exception("method findById :: " + e.getMessage());
		}
        return person;
    }
}