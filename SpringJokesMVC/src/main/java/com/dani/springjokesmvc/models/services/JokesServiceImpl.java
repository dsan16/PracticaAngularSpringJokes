package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dani.springjokesmvc.models.dao.IJokesDAO;
import com.dani.springjokesmvc.models.entities.Jokes;

@Service
public class JokesServiceImpl implements IJokesService{
	
	@Autowired
	private IJokesDAO jokesDao;

	@Override
	@Transactional(readOnly = true)
	public List<Jokes> findAll() {
		return (List<Jokes>) jokesDao.findAll();
	}

	@Override
	public Jokes save(Jokes cliente) {
		return jokesDao.save(cliente);
	}

	@Override
	public Jokes findById(int id) {
		return jokesDao.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		jokesDao.deleteById(id);
	}

	@Override
	public List<Jokes> findJokesWithoutPrimeraVez() {
		return jokesDao.findJokesWithoutPrimeraVez();
	}
}
