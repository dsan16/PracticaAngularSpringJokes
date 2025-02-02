package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Telefono;

public interface ITelefonoService{
	public Telefono findById(int id);
	public void delete(int id);
	public void deleteAll(List<Telefono> telefonos);
	public void save(Telefono telefono);
}
