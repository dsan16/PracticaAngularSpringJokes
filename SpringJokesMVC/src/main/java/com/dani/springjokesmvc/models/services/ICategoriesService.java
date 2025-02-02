package com.dani.springjokesmvc.models.services;

import java.util.List;

import com.dani.springjokesmvc.models.entities.Categories;

public interface ICategoriesService {
	public List<Categories> findAll();

	public Categories findById(int id);

	public Categories save(Categories categories);

	public void delete(int id);

}
