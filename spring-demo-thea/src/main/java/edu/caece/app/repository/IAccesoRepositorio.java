package edu.caece.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.caece.app.domain.Acceso;

public interface IAccesoRepositorio extends JpaRepository<Acceso, Long> {

}
