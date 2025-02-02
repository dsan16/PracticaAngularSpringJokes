package com.dani.springjokesmvc.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.Types;

public interface ITypesDAO extends CrudRepository<Types, Integer> {

}
