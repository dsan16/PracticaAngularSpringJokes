package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Flags;

public interface IFlagsService {
	public List<Flags> findAll();
	public Flags save(Flags flags);
	public Flags findById(int id);
	public void delete(int id);
}
