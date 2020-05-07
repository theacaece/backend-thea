package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Function;

public interface FunctionRepository extends JpaRepository<Function, Long> {

}
