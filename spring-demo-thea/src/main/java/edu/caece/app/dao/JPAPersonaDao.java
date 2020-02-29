package edu.caece.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import edu.caece.app.domain.Persona;

@Repository(value = "personaDao")
public class JPAPersonaDao implements IPersonaDao {

  @PersistenceContext
  private EntityManager em = null;

  @PersistenceContext
  public void setEntityManager(EntityManager em) throws Exception {
    this.em = em;
  }

  public void updatePersona(Persona persona) {
    em.merge(persona);
    em.flush();
  }

}
