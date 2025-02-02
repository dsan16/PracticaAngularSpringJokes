package com.dani.springjokesmvc.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.Flags;

public interface IFlagsDAO extends CrudRepository<Flags, Integer> {
}
