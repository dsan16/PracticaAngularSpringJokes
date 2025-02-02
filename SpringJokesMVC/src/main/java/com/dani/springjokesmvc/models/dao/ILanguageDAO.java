package com.dani.springjokesmvc.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.dani.springjokesmvc.models.entities.Language;

public interface ILanguageDAO extends CrudRepository<Language, Integer> {

}
