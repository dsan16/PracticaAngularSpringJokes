package com.dani.springjokesmvc.models.dao;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.dani.springjokesmvc.models.entities.Telefono;

public interface ITelefonoDAO extends CrudRepository<Telefono, Integer> {
	@Modifying
	@Query("DELETE FROM Telefono t WHERE t.primeraVez.id = :id")
    void deleteByPrimeraVezId(@Param("id") int primeraVezId);
}
