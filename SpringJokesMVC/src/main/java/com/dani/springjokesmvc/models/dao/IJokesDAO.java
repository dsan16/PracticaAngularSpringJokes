package com.dani.springjokesmvc.models.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.Jokes;

public interface IJokesDAO extends CrudRepository<Jokes, Integer> {
	@Query("SELECT j FROM Jokes j WHERE j.primeraVez IS NULL")
    List<Jokes> findJokesWithoutPrimeraVez();
}
