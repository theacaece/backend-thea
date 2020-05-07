package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Function;

public interface IFunctionRepository extends JpaRepository<Function, Long>{
	
}
