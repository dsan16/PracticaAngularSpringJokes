package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dani.springjokesmvc.models.dao.ITypesDAO;
import com.dani.springjokesmvc.models.entities.Types;

@Service
public class TypesServiceImpl implements ITypesService {
	
	@Autowired
	private ITypesDAO typesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Types> findAll() {
		return (List<Types>) typesDAO.findAll();
	}
	
	@Override
	public Types save(Types types) {
		return typesDAO.save(types);
	}
	
	@Override
	public Types findById(int id) {
		return typesDAO.findById(id).orElse(null);
	}
	
	@Override
	public void delete(int id) {
		typesDAO.deleteById(id);
	}

}
