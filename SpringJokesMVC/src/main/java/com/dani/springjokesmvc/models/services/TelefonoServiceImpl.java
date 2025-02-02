package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dani.springjokesmvc.models.dao.ITelefonoDAO;
import com.dani.springjokesmvc.models.entities.Telefono;

@Service
public class TelefonoServiceImpl implements ITelefonoService {

	@Autowired
	private ITelefonoDAO telefonoDao;
	
	@Override
	public Telefono findById(int id) {
		return telefonoDao.findById(id).orElse(null);
	}

	@Override
	public void delete(int id) {
		telefonoDao.deleteById(id);
	}
	
	@Override
	public void deleteAll(List<Telefono> telefonos) {
		telefonoDao.deleteAll(telefonos);
	}
	
	@Override
	public void save(Telefono telefono) {
		telefonoDao.save(telefono);
	}
}
