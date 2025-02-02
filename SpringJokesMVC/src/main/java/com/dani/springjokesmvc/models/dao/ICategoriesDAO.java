package com.dani.springjokesmvc.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.Categories;

public interface ICategoriesDAO extends CrudRepository<Categories, Integer> {

}
