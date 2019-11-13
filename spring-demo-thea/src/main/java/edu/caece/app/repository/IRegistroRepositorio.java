package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Registro;

public interface IRegistroRepositorio extends JpaRepository<Registro, Long> {

}
