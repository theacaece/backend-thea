package edu.caece.app.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

@Repository(value = "userDao")
public class JPAUserDao implements IUserDao {

    private EntityManager em = null;

    @PersistenceContext
    public void setEntityManager(EntityManager em) throws Exception {
        this.em = em;
    }

}