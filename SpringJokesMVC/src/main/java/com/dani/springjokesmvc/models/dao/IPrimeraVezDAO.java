package com.dani.springjokesmvc.models.dao;


import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.PrimeraVez;

public interface IPrimeraVezDAO extends CrudRepository<PrimeraVez, Integer> {
	@Query("SELECT j, p, t FROM Jokes j LEFT JOIN j.primeraVez p LEFT JOIN p.telefonos t")
    List<Object[]> findJokesWithPrimeraVezAndTelefonos();

    @Query("SELECT f.id, j.id, j.text1, l.language FROM Jokes j JOIN j.flagses f JOIN j.language l WHERE f.id = ?1")
    List<Object> findFlagsDetails(int flagId);
}
