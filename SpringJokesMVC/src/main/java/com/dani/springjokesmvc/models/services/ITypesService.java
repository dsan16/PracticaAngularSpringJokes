package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Types;

public interface ITypesService{
	public List<Types> findAll();
	public Types save(Types types);
	public Types findById(int id);
	public void delete(int id);
}
