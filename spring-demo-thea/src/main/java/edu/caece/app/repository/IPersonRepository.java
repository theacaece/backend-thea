package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Person;

public interface IPersonRepository extends JpaRepository<Person, Long> {

}
