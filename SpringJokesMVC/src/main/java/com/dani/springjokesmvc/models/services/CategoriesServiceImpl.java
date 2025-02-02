package com.dani.springjokesmvc.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dani.springjokesmvc.models.dao.ICategoriesDAO;
import com.dani.springjokesmvc.models.entities.Categories;

@Service
public class CategoriesServiceImpl implements ICategoriesService {
	@Autowired
	private ICategoriesDAO categoriesDAO;
	
	@Override
	@Transactional(readOnly = true)
	public List<Categories> findAll() {
		return (List<Categories>) categoriesDAO.findAll();
	}
	
	@Override
	public Categories save(Categories categories) {
		return categoriesDAO.save(categories);
	}
	
	@Override
	public Categories findById(int id) {
		return categoriesDAO.findById(id).orElse(null);
    }
	
	@Override
	public void delete(int id) {
		categoriesDAO.deleteById(id);
	}

}
