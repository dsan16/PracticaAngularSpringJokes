package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Jokes;

public interface IJokesService {
	public List<Jokes> findAll();
	public Jokes save(Jokes cliente);
	public Jokes findById(int id);
	public void delete(int id);
	public List<Jokes> findJokesWithoutPrimeraVez();
}
